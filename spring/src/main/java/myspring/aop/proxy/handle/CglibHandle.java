package myspring.aop.proxy.handle;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibHandle implements MethodInterceptor {
    private Object target;

    public CglibHandle(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("------插入前置通知代码-------------");
        // 执行相应的目标方法
        Object rs = method.invoke(target,args);
        System.out.println("------插入后置处理代码-------------");
        return rs;
    }
}
