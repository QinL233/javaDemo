package action.state;

public class 准备 extends 状态 {

	public 准备(售货机 m) {
		super(m);
		System.out.println("投币成功！现在有"+n.num+"枚硬币");
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
		if(!name.equals("冰红茶")) {
			System.out.println("没有"+name);
		}else if( n.num<5 ) {
			System.out.println("硬币不足");
		}else {
			n.num -=5;
			n.setSate(new 出货(n));
		}
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
