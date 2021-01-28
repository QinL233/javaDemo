package create.staticFactory;

public class Demo {

	public static void main(String[] args) {
		//正常创建
		/**
		 * 1.动态工厂
		 * 2.添加新的车型实现
		 * 3.添加新的工厂实现
		 */
		System.out.println("-----工厂------");
		new PersonFactory().createSomeone();
		new PersonFactory().createSomething();
		System.out.println("-----参数------");
		new PersonFactory().create("Someone");
		new PersonFactory().create("Something");
	}
}
