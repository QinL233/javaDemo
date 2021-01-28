package struct.proxy.simple;

public class Demo {

	public static void main(String[] args) {
		Person person=new Someone("lqz");
		person.action1();
		person.action2();
		person.action3();
		System.out.println("======请代理（aop）======");
		Person proxyPerson=new ProxyPerson(person);
		proxyPerson.action1();
		proxyPerson.action2();
		proxyPerson.action3();

	}

}
