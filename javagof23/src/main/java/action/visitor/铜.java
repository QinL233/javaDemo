package action.visitor;

public class 铜 implements 原料 {

	public String name="铜";
	@Override
	public void 操作(访问者 n) {
		n.访问(this);
	}

}
