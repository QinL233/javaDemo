package action.visitor;

public class 纸 implements 原料 {

	public String name="纸";
	@Override
	public void 操作(访问者 n) {
		n.访问(this);
	}
	
}
