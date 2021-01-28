package struct.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

//使用cglib实现代理，通过实现MethodInterceptor接口，通过fastClass创建对象实现代理模式，无需对象实现接口
public class ProxyPerson implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("begin");
        //执行方法
        methodProxy.invokeSuper(o,objects);
        System.out.println("end");
        return o;
    }
}
