package struct.composite;

import java.util.ArrayList;

public class 容器 implements 抽象 {

	private String name;
	private ArrayList<抽象> list=new ArrayList<抽象>();
	//构造方法获取name
	public 容器(String name) {
		super();
		this.name = name;
	}
	//给list添加add，remove，get
	public void add(抽象 n) {
		list.add(n);
	}
	public void remove(抽象 n) {
		list.remove(n);
	}
	public void get(int i) {
		list.get(i);
	}
	//实现方法
	@Override
	public double total() {
		double s=0;
		for (抽象 l : list) {
			s+=l.total();
		}
		return s;
	}

	@Override
	public void show() {
		for (抽象 l : list) {
			l.show();
		}
	}

}
