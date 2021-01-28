package struct.decorator;

public abstract class 装饰者 implements 抽象 {

	private 抽象 a;
	
	public 装饰者(抽象 a) {
		super();
		this.a = a;
	}

	@Override
	public String desc() {
		return a.desc();

	}

	@Override
	public int cost() {
		return a.cost();
	}

	public void add() {
		System.out.println("加一个");
	}
}
