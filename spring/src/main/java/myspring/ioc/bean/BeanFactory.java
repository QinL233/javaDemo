package myspring.ioc.bean;

/**
 * bean工厂
 */
public interface BeanFactory {
    //通过名称获取实例
    Object getBean(String name);
}
