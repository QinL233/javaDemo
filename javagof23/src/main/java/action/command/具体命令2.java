package action.command;

public class 具体命令2 implements 命令 {

	private 接收者2 a;
	具体命令2(){
		a=new 接收者2();
	}
	@Override
	public void function() {
		a.function();
	}

}
