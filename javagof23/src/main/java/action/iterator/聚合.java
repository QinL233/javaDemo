package action.iterator;

import java.util.ArrayList;
import java.util.List;

public class 聚合 implements 抽象聚合{

	private List<Object> list=new ArrayList<Object>();
	
	@Override
	public void add(Object obj) {
		list.add(obj);
	}

	@Override
	public void remove(Object obj) {
		list.remove(obj);
	}

	@Override
	public 抽象迭代器 get() {
		return (new 迭代器(list));
	}

}
