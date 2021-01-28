package myspring.mvc.servlet;

import myspring.ioc.context.impl.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    private WebApplicationContext context;
    public static final String ROOT_CXT_ATTR="ROOT_CXT";

    /**
     * 初始化
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("contextInitialized");
        ServletContext servletContext = servletContextEvent.getServletContext();
        if( this.context == null){
            String config=servletContext.getInitParameter("contextConfigLocation");
            this.context=new WebApplicationContext(config);
            //存放上下文
            servletContext.setAttribute(ROOT_CXT_ATTR,this.context);
        }
    }

    /**
     *  删除
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("contextDestroyed");
        context=null;
    }
}
