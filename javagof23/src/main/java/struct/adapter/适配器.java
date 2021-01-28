package struct.adapter;

public interface 适配器 {
	void 实现();
}

/**
 * 1.使用继承
 * @author LQZ
 *
 */
class 适配器1 extends 键盘 implements 适配器 {

	@Override
	public void 实现() {
		super.功能();
	}
}

/**
 * 1.使用组合
 * @author LQZ
 *
 */
class 适配器2  implements 适配器 {
	
	private 键盘 name;
	
	public 适配器2(键盘 name) {
		super();
		this.name = name;
	}

	@Override
	public void 实现() {
		name.功能();
	}
}