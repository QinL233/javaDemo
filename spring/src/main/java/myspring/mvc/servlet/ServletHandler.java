package myspring.mvc.servlet;

import myspring.ioc.context.ApplicationContext;
import myspring.mvc.servlet.model.ModelAndView;
import myspring.utils.CommonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class ServletHandler {

    private ApplicationContext context;

    public ServletHandler(ApplicationContext context) {
        this.context = context;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        //只留请求资源路径
        url=url.replace(contextPath,"").replaceAll("/+","/");
        if(context.getHandlerMethod(url) == null){
            response.getWriter().write("404 NOT FOUND");
            return;
        }

        Method method = context.getHandlerMethod(url);

        //获取方法参数
        Class<?>[] parameterTypes = method.getParameterTypes();
        //获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //保存参数值
        Object[] params=new Object[parameterTypes.length];
        for(int i=0;i<parameterTypes.length;i++){
            String paramName = parameterTypes[i].getSimpleName();
            if("HttpServletRequest".equals(paramName)){
                params[i]=request;
                continue;
            }
            if("HttpServletResponse".equals(paramName)){
                params[i]=response;
                continue;
            }
            if("String".equals(paramName)){
                for(Map.Entry<String,String[]> map:parameterMap.entrySet()){
                    params[i]=Arrays.toString(map.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                }
            }
        }
        //利用反射调用method
        try {
            Object result = method.invoke(context.getController(url), params);
            if(result instanceof ModelAndView){
                //处理视图
                ModelAndView mav = (ModelAndView) result;
                String path=mav.getName();
                if(!CommonUtil.isEmpty(path)){
                    Map<String, Object> attributes = mav.getAttributes();
                    //属性设置到servlet响应
                    for(Map.Entry<String,Object> attribute:attributes.entrySet()){
                        request.setAttribute(attribute.getKey(),attribute.getValue());
                    }
                    //页面发送到jsp文件中
                    request.getRequestDispatcher(CommonUtil.JSP_PATH_PREFIX+path).forward(request,response);
                }
            }else{
                //直接返回数据
                if(result != null){
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(result.toString());
                    writer.flush();
                    writer.close();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
