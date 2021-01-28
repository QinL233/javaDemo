package myspring.mvc.servlet;

import myspring.ioc.context.impl.WebApplicationContext;
import myspring.utils.CommonUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = -4645578815475241176L;

    private WebApplicationContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        Object oldContext = config.getServletContext().getAttribute(ContextLoaderListener.ROOT_CXT_ATTR);
        this.context =oldContext == null ? new WebApplicationContext(): (WebApplicationContext) oldContext;
        ServletContext servletContext = config.getServletContext();
        //注册jsp
        ServletRegistration jsp = servletContext.getServletRegistration("jsp");
        jsp.addMapping(CommonUtil.JSP_PATH_PREFIX + "*");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    /**
     * 处理servlet
     * @param request
     * @param response
     */
    private void doDispatch(HttpServletRequest request,HttpServletResponse response){

    }
}
