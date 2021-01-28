package struct.flyweight;

import java.util.HashMap;
import java.util.Map;

public class pool {

	//池
	private Map<String, iphone> pool =new HashMap<>();
	
	private iphone p;
	
	public iphone getPhone(String name) {
		//判断pool中是否存在name
		if(!pool.containsKey(name)) {
			pool.put(name, new iphone(name));
		}
		return pool.get(name);
	}
	
	public void showSize() {
		System.out.println(pool.size());
	}
}
