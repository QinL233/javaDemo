package struct.decorator;

public class client {

	public static void main(String[] args) {
		/**
		 * 1.在不动抽象的情况下，动态给抽象方法添加新功能
		 */
		抽象 a=new 煎饼();
		System.out.println(a.desc());
		System.out.println("价格："+a.cost());
		
		a=new 鸡蛋装饰器(a);
		System.out.println(a.desc());
		System.out.println("价格："+a.cost());
		
		a=new 培根装饰器(a);
		System.out.println(a.desc());
		System.out.println("价格："+a.cost());
		
	}

}
