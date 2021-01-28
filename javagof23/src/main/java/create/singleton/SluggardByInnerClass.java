package create.singleton;

import java.io.Serializable;

/**
 * 静态内部类实现
 * @author LQZ
 *
 */
public class SluggardByInnerClass implements Serializable {

	//1.私有化构造器
	private SluggardByInnerClass() {
		
	}
	
	//2.使用私有静态内部类创建对象：第一次加载时，不会初始化静态内部类
	private static class SingletonClass{
		//实现线程安全
		private static final SluggardByInnerClass singleton=new SluggardByInnerClass();
	}
	
	//3.开放获取对象方法
	public static SluggardByInnerClass getSingleton() {
		return SingletonClass.singleton;
	}

	//4.重写object方法，防止序列化创建，在序列化获取对象时，直接返回
	private Object readResolve() throws Exception{
		return SingletonClass.singleton;
	}
	
}
