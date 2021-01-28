package struct.decorator;

public class 鸡蛋装饰器 extends 装饰者 {

	public 鸡蛋装饰器(抽象 a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String desc() {
	
		return super.desc()+"加一个鸡蛋";
	}

	@Override
	public int cost() {
		// TODO Auto-generated method stub
		return super.cost()+5;
	}


}
