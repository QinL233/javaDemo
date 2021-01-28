package action.state;

public class 售货机 {
	private 状态 state;
	public int num=0;//硬币数
	
	public 售货机() {
		//一开始是初始状态，有一台关机状态的售货机
		state=new 关机(this);
	}
	
	public void setSate(状态 state) {
		this.state=state;
	}
	
	public void 开机() {
		state.开机();
	}

	public void 投币(int num) {
		state.投币(num);
	}

	public void 选择(String name) {
		state.选择(name);
	}
	public void 退币() {
		state.退币();
	}
	
	public void 关机() {
		state.关机();
	}
}
