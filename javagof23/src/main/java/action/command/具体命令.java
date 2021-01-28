package action.command;

public class 具体命令 implements 命令 {

	private 接收者 a;
	具体命令(){
		a=new 接收者();
	}
	@Override
	public void function() {
		a.function();
	}

}
