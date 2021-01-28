package action.observer;

public class Demo {
	public static void main(String[] args) {
		目标 n=new 网红();
		观察者 a=new 网友();
		观察者 b=new 水军();
		n.add(a);

		n.通知();
		n.add(b);
		n.通知();
	}
}
