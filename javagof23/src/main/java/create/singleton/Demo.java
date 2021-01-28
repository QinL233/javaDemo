package create.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

public class Demo {

	public static void main(String[] args) throws Exception {
		//hungry();
		hungryByEnum();
		//sluggard();
		//sluggardByInnerClass();
	}

	private static void hungry()throws Exception {
		System.out.println("正常获取");
		System.out.println(Hungry.getSingleton());
		System.out.println(Hungry.getSingleton());
		System.out.println(Hungry.getSingleton());
		//序列化获取
		//对象需要实现serializable接口
		//io中调用object 的readResolve方法重新获取实例，因此单例要重写readResolve方法
		System.out.println("序列化获取");
		FileOutputStream fos=new FileOutputStream("c:/test/test.txt");//把对象序列化写入
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		//获取实例并写入文本
		oos.writeObject(Hungry.getSingleton());
		oos.writeObject(Hungry.getSingleton());
		oos.writeObject(Hungry.getSingleton());
		oos.close();
		fos.close();
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("c:/test/test.txt"));
		//读取文本实例
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println("反射获取");
		//反射将直接获取构造器创建新的实例，因此需要在私有构造器时添加判断做异常抛出
		Class<Hungry> clazz=(Class<Hungry>) Class.forName("gof23.create.singleton.Hungry");
		//由于构造器私有化因此需要获取构造器
		Constructor<Hungry> c=clazz.getDeclaredConstructor(null);//获取构造器
		c.setAccessible(true);//跳过private权限
		//通过构造器创建对象
		System.out.println(c.newInstance());
		System.out.println(c.newInstance());
		System.out.println(c.newInstance());
	}

	private static void hungryByEnum()throws Exception {
		System.out.println("正常获取");
		System.out.println(HungryByEnum.singletion);
		System.out.println(HungryByEnum.singletion);
		System.out.println(HungryByEnum.singletion);
		//序列化获取
		//对象需要实现serializable接口
		//io中调用object 的readResolve方法重新获取实例，因此单例要重写readResolve方法
		System.out.println("序列化获取");
		FileOutputStream fos=new FileOutputStream("c:/test/test.txt");//把对象序列化写入
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		//获取实例并写入文本
		oos.writeObject(HungryByEnum.singletion);
		oos.writeObject(HungryByEnum.singletion);
		oos.writeObject(HungryByEnum.singletion);
		oos.close();
		fos.close();
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("c:/test/test.txt"));
		//读取文本实例
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println("反射获取");
		//反射将直接获取构造器创建新的实例，因此需要在私有构造器时添加判断做异常抛出
		Class<HungryByEnum> clazz=(Class<HungryByEnum>) Class.forName("gof23.create.singleton.HungryByEnum");
		Constructor<HungryByEnum> c=clazz.getDeclaredConstructor(null);//获取构造器
		c.setAccessible(true);//跳过权限：private
		//通过构造器创建对象
		System.out.println(c.newInstance());
		System.out.println(c.newInstance());
		System.out.println(c.newInstance());
	}

	private static void sluggard() throws Exception {
		System.out.println("正常获取");
		System.out.println(Sluggard.getSingleton());
		System.out.println(Sluggard.getSingleton());
		System.out.println(Sluggard.getSingleton());
		//序列化获取
		//对象需要实现serializable接口
		//io中调用object 的readResolve方法重新获取实例，因此单例要重写readResolve方法
		System.out.println("序列化获取");
		FileOutputStream fos=new FileOutputStream("c:/test/test.txt");//把对象序列化写入
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		//获取实例并写入文本
		oos.writeObject(Sluggard.getSingleton());
		oos.writeObject(Sluggard.getSingleton());
		oos.writeObject(Sluggard.getSingleton());
		oos.close();
		fos.close();
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("c:/test/test.txt"));
		//读取文本实例
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println("反射获取");
		//反射将直接获取构造器创建新的实例，因此需要在私有构造器时添加判断做异常抛出
		Class<Sluggard> clazz=(Class<Sluggard>) Class.forName("gof23.create.singleton.Sluggard");
		Constructor<Sluggard> c=clazz.getDeclaredConstructor(null);//获取构造器
		c.setAccessible(true);//跳过权限：private
		//通过构造器创建对象
		System.out.println(c.newInstance());
		System.out.println(c.newInstance());
		System.out.println(c.newInstance());
	}

	private static void sluggardByInnerClass()throws Exception {
		System.out.println("正常获取");
		System.out.println(SluggardByInnerClass.getSingleton());
		System.out.println(SluggardByInnerClass.getSingleton());
		System.out.println(SluggardByInnerClass.getSingleton());
		//序列化获取
		//对象需要实现serializable接口
		//io中调用object 的readResolve方法重新获取实例，因此单例要重写readResolve方法
		System.out.println("序列化获取");
		FileOutputStream fos=new FileOutputStream("c:/test/test.txt");//把对象序列化写入
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		//获取实例并写入文本
		oos.writeObject(SluggardByInnerClass.getSingleton());
		oos.writeObject(SluggardByInnerClass.getSingleton());
		oos.writeObject(SluggardByInnerClass.getSingleton());
		oos.close();
		fos.close();
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("c:/test/test.txt"));
		//读取文本实例
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println("反射获取");
		//反射将直接获取构造器创建新的实例，因此需要在私有构造器时添加判断做异常抛出
		Class<SluggardByInnerClass> clazz=(Class<SluggardByInnerClass>) Class.forName("gof23.create.singleton.SluggardByInnerClass");
		Constructor<SluggardByInnerClass> c=clazz.getDeclaredConstructor(null);//获取构造器
		c.setAccessible(true);//跳过权限：private
		//通过构造器创建对象
		System.out.println(c.newInstance());
		System.out.println(c.newInstance());
		System.out.println(c.newInstance());
	}

}
