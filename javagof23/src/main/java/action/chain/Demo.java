package action.chain;

public class Demo {

	public static void main(String[] args) {
		int i=19;
		//可以随意更改判断条件，并且添加
		Chain first=new First(i<5);
		Chain second=new Second(i<10);
		Chain third=new Third(i<20);
		Chain aFinal=new Final(i<25);

		//构建责任链：可以随意改变链顺序
		first.setNext(second);
		second.setNext(third);
		third.setNext(aFinal);
		
		first.action();
	}
}
