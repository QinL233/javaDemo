package action.state;

public class 待机 extends 状态 {

	public 待机(售货机 m) {
		super(m);
	}

	@Override
	public void 开机() {
		System.out.println("无法开机");
	}

	@Override
	public void 投币(int num) {
		n.num +=num;
		n.setSate(new 准备(n));

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
