package create.singleton;

import java.io.Serializable;

/**
 * 单例：懒汉,在获取对象方法时创建实例
 * @author LQZ
 *
 */
public class Sluggard implements Serializable {

	//1.创建该类对象的属性
	private static Sluggard singleton=null;

	//2.私有化构造器
	private Sluggard() {
		//防止在实例已存在时，反射重复创建
		//但若实例不存在，反射可以一直创建新对象
		if(singleton != null) {
			throw new IllegalArgumentException("非法访问");
		}
	}

	//3.开放：synchronized保证线程安全，使其对象仅有一个
	public static synchronized Sluggard getSingleton() {
		//若singleton2不存在则创建，存在则返回。
		return (singleton==null)?singleton=new Sluggard():singleton;
	}
	
	//4.重写object方法，防止序列化创建，在序列化获取对象时，直接返回
	private Object readResolve() throws Exception{
		return singleton;
	}
}
