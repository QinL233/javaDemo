package struct.facade;

public class 外观 {

	private static CPU a;
	private static GPU b;
	static{
		a=new CPU();
		b=new GPU();
	}
	public void start() {
		System.out.println("computer start");
		this.a.start();
		this.b.start();
	}
	public void stop() {
		this.a.stop();
		this.b.stop();
		System.out.println("computer stop");
	}
}
