package action.command;

public class 调用者 {

	private 命令 a;
	public void set(命令 a) {
		this.a=a;
	}
	public void function() {
		a.function();
	}
}
