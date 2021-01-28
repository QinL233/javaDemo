package struct.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//动态代理中，代理对象无需实现被代理接口，直接利用反射机制实现代理多个接口
public class ProxyPerson implements InvocationHandler {

	//对象组合
	private Person person;
	public ProxyPerson(Person person) {
		this.person =person;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		/**
		 * 1.proxy对象
		 * 2.方法
		 * 3.参数
		 * 4.所有方法都会经过该方法，因此可以在该方法内做判断（aop）
		 */
		if(method.getName().equals("action1")) {
			System.out.println("proxy执行任务1");
		}else if(method.getName().equals("action3")) {
			System.out.println("proxy执行任务3");
		}else
			method.invoke(person, args);
		return null;
	}

}
