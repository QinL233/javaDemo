package struct.adapter;

public class client {
	public static void main(String[] args) {
		//使用继承实现，java继承的缺点
		new 笔记本().打字();
		//使用对象组合实现，打字需要传入键盘，更合理
		键盘 name = new 键盘();
		new 笔记本().打字2(name);
	}
}
