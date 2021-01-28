package struct.proxy.simple;

public class ProxyPerson implements Person {

	private Person person;
	public ProxyPerson(Person person){
		this.person=person;
	}

	@Override
	public void action1() {
		System.out.println("proxy执行任务1");
	}

	@Override
	public void action2() {
		person.action2();
	}

	@Override
	public void action3() {
		System.out.println("proxy执行任务3");
	}
}
