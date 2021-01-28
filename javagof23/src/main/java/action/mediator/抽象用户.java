package action.mediator;

public interface 抽象用户 {

	//接受
	public void get();
	//发送
	public void send();
	//登记到中介
	public void register(抽象中介者 n);
}
