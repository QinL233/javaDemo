package create.singleton;

/**
 * 枚举方式--枚举本身自带static并且单例：没有延时加载，避免反射创建对象
 * @author LQZ
 *
 */
public enum HungryByEnum {
	
	//1.定义属性：本身就是单例额
	singletion;
	
	//2.添加操作
	public void singletonOperation() {
		
	}
}
