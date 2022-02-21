package create.singleton;

import java.io.Serializable;

/**
 * 静态内部类实现
 * 1、当该类被加载时，其静态内部类不会加载进内存
 * 2、当该类函数被调用时，才会开始初始化内部类实例
 * 3、由jvm提供对线程安全的支持
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
