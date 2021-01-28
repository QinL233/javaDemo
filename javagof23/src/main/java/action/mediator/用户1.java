package action.mediator;

public class 用户1 implements 抽象用户 {

	private 抽象中介者 n;
	
	@Override
	public void get() {
		System.out.println("用户1 get");

	}

	@Override
	public void send() {
		System.out.println("用户1 set");
		//转发
		n.relay(this);

	}

	@Override
	public void register(抽象中介者 n) {
		this.n=n;
		
	}

}
