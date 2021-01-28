package struct.decorator;

public class 培根装饰器 extends 装饰者 {

	public 培根装饰器(抽象 a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String desc() {
	
		return super.desc()+"加一个培根";
	}

	@Override
	public int cost() {
		// TODO Auto-generated method stub
		return super.cost()+5;
	}


}
