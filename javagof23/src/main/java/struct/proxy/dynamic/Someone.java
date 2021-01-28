package struct.proxy.dynamic;

public class Someone implements Person {
	private String name;
	public Someone(String name){
		this.name=name;
	}

	@Override
	public void action1() {
		System.out.println(name+"执行任务1");
	}

	@Override
	public void action2() {
		System.out.println(name+"执行任务2");
	}

	@Override
	public void action3() {
		System.out.println(name+"执行任务3");
	}
}
