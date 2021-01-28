package action.state;

public class 关机 extends 状态 {

	public 关机(售货机 m) {
		super(m);
	}

	@Override
	public void 开机() {
		n.setSate(new 待机(n));
		System.out.println("开机成功");
	}

	@Override
	public void 投币(int num) {
		System.out.println("无法投币");
	}

	@Override
	public void 选择(String name) {
		System.out.println("无法选择");
	}

	@Override
	public void 退币() {
		System.out.println("无法退币");
	}
	
	@Override
	public void 关机() {
		System.out.println("无法关机");
	}

}
