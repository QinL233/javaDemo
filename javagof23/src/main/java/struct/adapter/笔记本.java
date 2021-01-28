package struct.adapter;

public class 笔记本 {

	public void 打字() {
		System.out.println("继承实现");
		new 适配器1().实现();
	}
	
	public void 打字2(键盘 name) {
		System.out.println("对象组合实现");
		new 适配器2(name).实现();
	}
}
