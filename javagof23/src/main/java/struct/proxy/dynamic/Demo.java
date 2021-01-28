package struct.proxy.dynamic;

import java.lang.reflect.Proxy;

public class Demo {

	public static void main(String[] args) {
		Person person=new Someone("lqz");
        person.action1();
        person.action2();
        person.action3();
		System.out.println("======请代理（aop）======");
		ProxyPerson proxy= new ProxyPerson(person);
		//使用java 内部proxy
		Person proxyInstance = (Person) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] {Person.class},proxy);
        proxyInstance.action1();
        proxyInstance.action2();
        proxyInstance.action3();

	}

}
