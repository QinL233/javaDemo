package myspring.aop.proxy;

import myspring.aop.proxy.handle.JdkHandle;
import myspring.aop.proxy.service.Service;
import myspring.aop.proxy.service.impl.ServiceImpl;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class JdkProxy {
    @Test
    public void test1() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        /**
         * 使用JDK动态代理的五大步骤:
         * 1.通过实现InvocationHandler接口来自定义自己的InvocationHandler;
         * 2.通过Proxy.getProxyClass获得动态代理类
         * 3.通过反射机制获得代理类的构造方法，方法签名为getConstructor(InvocationHandler.class)
         * 4.通过构造函数获得代理对象并将自定义的InvocationHandler实例对象传为参数传入
         * 5.通过代理对象调用目标方法
         */
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Class<?> proxyClass = Proxy.getProxyClass(Service.class.getClassLoader(), Service.class);
        Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
        Service service = (Service)constructor.newInstance((new JdkHandle(new ServiceImpl())));
        service.method1();
        service.method2("method2");
    }

    @Test
    public void test2(){
        Service service=(Service)Proxy.newProxyInstance(Service.class.getClassLoader(),
                new Class[]{Service.class},
                new JdkHandle(new ServiceImpl()));
        service.method1();
        service.method2("method2");
    }
}
