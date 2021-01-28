package action.state;

public class 出货 extends 状态 {

	public 出货(售货机 m) {
		super(m);
		System.out.println("出货成功！");
	}

	@Override
	public void 开机() {
		System.out.println("无法开机");
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
		System.out.println("退回"+n.num+"枚硬币");
		n.num=0;
		n.setSate(new 待机(n));
	}
	
	@Override
	public void 关机() {
		System.out.println("即将关机");
		n.setSate(new 关机(n));
	}
}
