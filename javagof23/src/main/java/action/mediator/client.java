package action.mediator;

public class client {
	public static void main(String[] args) {
		抽象中介者 n=new 中介者();
		抽象用户 a,b,c;
		a=new 用户1();
		b=new 用户2();
		c=new 用户3();
		//登记用户
		n.register(a);
		n.register(b);
		n.register(c);
		//交流（广播）
		a.send();
		System.out.println("-----");
		c.send();
	}
}
