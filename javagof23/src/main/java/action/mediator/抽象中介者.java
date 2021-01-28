package action.mediator;

public interface 抽象中介者 {

	//登记
	public void register(抽象用户 n);
	//转发
	public void relay(抽象用户 n);
}
