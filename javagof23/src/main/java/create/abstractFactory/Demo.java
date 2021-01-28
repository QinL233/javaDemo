package create.abstractFactory;

public class Demo {

	public static void main(String[] args) {
		/**
		 * 1.抽象工厂以群为单位 ，满足创建多个部件组成一个整体
		 * 2.新增一个车型实现
		 * 3.新增一个工厂实现
		 */
		 new PersonFactory().create("Man").getName();
		 new PersonFactory().create("Man").addProperty("Good");
		 new PersonFactory().create("Man").addProperty("Bad");
		new PersonFactory().create("Woman").getName();
		new PersonFactory().create("Woman").addProperty("Good");
		new PersonFactory().create("Woman").addProperty("Bad");
	}
}
