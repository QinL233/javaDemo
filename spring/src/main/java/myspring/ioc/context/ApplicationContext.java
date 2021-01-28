package myspring.ioc.context;

import myspring.ioc.bean.BeanFactory;

import java.lang.reflect.Method;

/**
 * 启动spring的核心方法，用于初始化bean和context，对标spring的ConfigurableApplicationContext
 */
public interface ApplicationContext extends BeanFactory {

    void refresh() throws Exception;

    Method getHandlerMethod(String url);

    Object getController(String url);
}
