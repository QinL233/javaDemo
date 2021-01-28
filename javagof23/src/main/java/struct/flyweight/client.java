package struct.flyweight;

public class client {

	public static void main(String[] args) {
		pool a=new pool();
		phone p=a.getPhone("苹果6");
		p.name();
		p.function("打电话");
		System.out.println(p);
		phone p1=a.getPhone("苹果6s");
		p1.name();
		p1.function("打王者");
		System.out.println(p1);
		phone p2=a.getPhone("苹果x");
		p2.name();
		p2.function("看B站");
		System.out.println(p2);
		phone p3=a.getPhone("苹果6");
		p3.name();
		p3.function("看youtube");
		System.out.println(p3);
	}

}
