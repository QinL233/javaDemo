package create.builder;

public class Demo {
	
	public static void main(String[] args) {
		/**
		 * 1.解耦
		 * 2.建造者负责建造参数
		 * 3.组装者负责建造顺序
		 */
		Assembler Assembler;
		Assembler = new Assembler(new GoodManBuilder());
		Assembler = new Assembler(new BadManBuilder());
		Person person = Assembler.assemble();
		System.out.println(person.getSex());
		System.out.println(person.getProperty());

	}
}
