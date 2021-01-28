package action.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class 目标 {

	protected List<观察者> list=new ArrayList<>();
	
	public void add(观察者 a) {
		list.add(a);
	}
	
	public void remove(观察者 a ) {
		list.remove(a);
	}
	
	public abstract void 通知();
}
