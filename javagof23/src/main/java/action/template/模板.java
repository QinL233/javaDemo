package action.template;

public abstract class 模板 {

	public void function() {
		f1();
		f2();
		f3();
	}
	
	public void f1() {
		System.out.println("f111");
	}
	
	public abstract void f2();
	
	public void f3() {
		System.out.println("f333333");
	}
}
