package struct.composite;

public class 叶子 implements 抽象 {

	private String name;
	private int num;
	private double price;
	//获取属性值
	public 叶子(String name, int num, double price) {
		super();
		this.name = name;
		this.num = num;
		this.price = price;
	}
	//实现方法
	@Override
	public double total() {
		// TODO Auto-generated method stub
		return num*price;
	}

	@Override
	public void show() {
		System.out.println("叶子 [name=" + name + ", num=" + num + ", price=" + price + "]");

	}

}
