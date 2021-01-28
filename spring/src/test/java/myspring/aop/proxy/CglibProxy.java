package myspring.aop.proxy;

import myspring.aop.proxy.handle.CglibHandle;
import myspring.aop.proxy.service.impl.Service2Impl;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

public class CglibProxy {
    @Test
    public void test1(){
        //1.创建Enhancer
        Enhancer enhancer = new Enhancer();
        //2.提供要代理类的class
        enhancer.setSuperclass(Service2Impl.class);
        //3.提供代理handle
        enhancer.setCallback(new CglibHandle(new Service2Impl()));
        //4.获取代理对象
        Service2Impl service=(Service2Impl) enhancer.create();
        service.method1();
        service.method2("method2");
    }
}
