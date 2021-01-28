package action.state;

/**
 * 类名对应类应有的状态
 * @author LQZ
 *
 */
public abstract class 状态 {

	//绑定售货机
	protected 售货机 n;
	public 状态(售货机 m) {
		n=m;
		System.out.println("当前状态为："+this.getClass().getName());
	}
	
	public abstract void 开机();
	public abstract void 投币(int num);
	public abstract void 选择(String name);
	public abstract void 退币();
	public abstract void 关机();
}
