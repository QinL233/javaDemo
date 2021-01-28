package myspring.aop.proxy.handle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/***
 * jdk代理
 */
public class JdkHandle implements InvocationHandler {
    private Object target;

    public JdkHandle(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------插入前置通知代码-------------");
        // 执行相应的目标方法
        Object rs = method.invoke(target,args);
        System.out.println("------插入后置处理代码-------------");
        return rs;
    }
}
