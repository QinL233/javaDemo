package action.strategy;

public class client {
	public static void main(String[] args) {
		//策略模式
		抽象 a=new 加法();
		抽象 b=new 减法();
		抽象 c=new 除法();
		
		环境 n=new 环境(a);
		n.方法(5, 8);
		n.set(b);
		n.方法(5, 8);
		n.set(c);
		n.方法(5, 8);
	}
}
