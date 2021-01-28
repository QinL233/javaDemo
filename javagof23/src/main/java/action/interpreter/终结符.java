package action.interpreter;

import java.util.HashSet;
import java.util.Set;

public class 终结符 implements 表达式 {

	private Set<String> set=new HashSet<>();
	
	public 终结符(String[] str) {
		//将数组的元素添加到集合里
		for (String s : str) {
			set.add(s);
		}
	}
	
	@Override
	public boolean 解释(String str) {
		//判断该string是否存在于set中
		if(set.contains(str)) {
			return true;
		}
		return false;
	}

}
