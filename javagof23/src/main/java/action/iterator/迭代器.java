package action.iterator;

import java.util.List;

public class 迭代器 implements 抽象迭代器 {

	private List<Object> list=null;
	private int index=-1;
	public 迭代器(List<Object> list) {
		this.list=list;
	}
	
	@Override
	public Object first() {
		index=0;
		Object obj=list.get(index);
		return obj;
	}

	@Override
	public Object next() {
		Object obj=null;
		if(this.hasNext()) {
			
			obj=list.get(++index);
		}
		return obj;
	}

	//判断使用index索引判断list是否还有值
	@Override
	public boolean hasNext() {
		if(index < list.size()-1) {
			return true;
		}else {
			return false;
		}
	}

}
