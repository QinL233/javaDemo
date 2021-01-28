package struct.bridge;

public abstract class 电脑 {

	protected 品牌 name;
	
	public 电脑(品牌 name) {
		this.name=name;
	}
	public void getName() {
		name.name();
	}
}

class 笔记本 extends 电脑 {

	public 笔记本(品牌 name) {
		super(name);
		name.name();
		System.out.println("笔记本");
	}
}

class 台式 extends 电脑 {

	public 台式(品牌 name) {
		super(name);
		name.name();
		System.out.println("台式");
	}
}

class 平板 extends 电脑 {

	public 平板(品牌 name) {
		super(name);
		name.name();
		System.out.println("平板");
	}
}