package action.command;

public class client {

	public static void main(String[] args) {
		调用者 b=new 调用者();
		命令 a =new 具体命令();
		b.set(a);
		b.function();
		命令 c =new 具体命令2();
		b.set(c);
		b.function();
		/**
		 * 1.调用者是程序入口
		 * 2.调用者需要传入对应的命令，从而调用对应命令的方法
		 * 3.通过面向接口编程，使得命令更加灵活--->命令（接口）>>具体命令（实现）
		 * 4.具体命令则绑定对应的接收者
		 * 5.通过一个调用者，传入不同命令，实现不同的调用效果
		 */
	}

}
