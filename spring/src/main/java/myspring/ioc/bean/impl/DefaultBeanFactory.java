package myspring.ioc.bean.impl;

import myspring.ioc.bean.BeanFactory;
import myspring.ioc.bean.model.BeanDefinition;
import myspring.utils.CommonUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring 容器
 */
public class DefaultBeanFactory implements BeanFactory {

    //存储bean
    private List<BeanDefinition> beanDefinitionList = new ArrayList<>();

    //存储实例
    private Map<String,Object> beanClassMap =new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) {
        return beanClassMap.get(name);
    }

    public List<BeanDefinition> getBeanDefinitionList() {
        return beanDefinitionList;
    }

    public Map<String, Object> getBeanClassMap() {
        return beanClassMap;
    }

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitions){
        this.beanDefinitionList.addAll(beanDefinitions);
    }

    //实例化bean
    public void instanceBeans(){
        //将beanDefinition中定义的实例全部实例并放入beanClass
        if(!CommonUtil.isEmpty(beanDefinitionList)){
            for (BeanDefinition beanDefinition : beanDefinitionList) {
                if(!CommonUtil.isEmpty(beanDefinition.getClassName())){
                    try {
                        beanClassMap.put(beanDefinition.getId(),Class.forName(beanDefinition.getClassName()).newInstance());
                        System.out.println(beanDefinition.getId() + "\t" + beanDefinition.getClassName() + "\t instance success");
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //属性注入
    public void injectProperty(){
        if(!CommonUtil.isEmpty(beanDefinitionList)) {
            for (BeanDefinition beanDefinition : beanDefinitionList) {
                Object bean = beanClassMap.get(beanDefinition.getId());
                if(bean !=null){
                    try {
                        //1.获取bean information
                        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                        //2.BeanInfo的属性描述器PropertyDescriptor可以获得对应属性的getter/setter
                        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                        //3.遍历beanDefinition要注册的属性
                        beanDefinition.getPropertyDefinitionList().forEach(propertyDefinition->{
                            //4.寻找对应的属性描述器
                            for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
                                if(propertyDefinition.getName().equals(propertyDescriptor.getName())){
                                    //5.获取属性setter方法
                                    Method setter = propertyDescriptor.getWriteMethod();
                                    if(setter !=null){
                                        //6.判断当前属性是否存在引用，存在则直接从map中获取引用并注入
                                        Object value=null;
                                        if(CommonUtil.isEmpty(propertyDefinition.getRef())){
                                            value=CommonUtil.convert(propertyDefinition.getValue(),propertyDescriptor.getPropertyType());
                                        }else {
                                            value=beanClassMap.get(propertyDefinition.getRef());
                                        }
                                        //7.保证可以访问private
                                        setter.setAccessible(true);
                                        //8.执行setter
                                        try {
                                            setter.invoke(bean,value);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                }
                            }
                        });
                    } catch (IntrospectionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
