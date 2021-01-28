package action.mediator;

import java.util.ArrayList;
import java.util.List;

public class 中介者 implements 抽象中介者 {

	private List<抽象用户> list=new ArrayList<抽象用户>();
	@Override
	public void register(抽象用户 n) {
		//如果不存在则登记用户
		if(!list.contains(n)) {
			list.add(n);
			//调用用户的register把当前中介登记
			n.register(this);
		}
	}

	@Override
	public void relay(抽象用户 n) {
		//遍历list，把非当前用户的内容转发给所有其他用户
		for (抽象用户 l : list) {
			if(!l.equals(n)) {
				l.get();
			}
		}
	}

}
