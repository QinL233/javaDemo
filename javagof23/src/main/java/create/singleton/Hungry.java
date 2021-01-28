package create.singleton;

import java.io.Serializable;

/**
 * 单例模式--饿汉,在对象创建开始时就创建实例，获取方法中返回该实例
 * @author LQZ
 *
 */
public class Hungry implements Serializable {


	//1.在该类下自己生成该对象，并仅不对外开发：线程天然安全（无法延时加载）
	private static Hungry singleton=new Hungry();

	//2.私有化构造器，使其他无法创建该对象
	private Hungry() {
		
	}

	//3.开放一个方法基于获取对象：无同步，调用效率搞
	public static Hungry getSingleton() {
		return singleton;
	}

	//4.重写object方法，防止序列化创建，在序列化获取对象时，直接返回
	private Object readResolve() throws Exception{
		return singleton;

	}
}
