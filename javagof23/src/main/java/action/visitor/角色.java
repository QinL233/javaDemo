package action.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class 角色 {

	private List<原料> list=new ArrayList<原料>();
	
	public void 访问(访问者 n) {
		Iterator<原料> i = list.iterator();
		while(i.hasNext()) {
			i.next().操作(n);
		}
	}
	
	public void add(原料 n) {
		list.add(n);
	}
	
	public void remove(原料 n) {
		list.remove(n);
	}
}
