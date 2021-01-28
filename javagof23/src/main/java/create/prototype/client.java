package create.prototype;

import java.io.*;
import java.util.Date;

public class client {

	public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {

		/**
		 * 1.浅克隆：克隆对象的属性由于是引用关系，克隆出来的对象的属性也是引用关系
		 * 2.结果是当引用对象改变时，克隆对象引用改变，同时克隆出来的也会改变，因此这个克隆对象不能作为模板使用
		 */
		test();
		//test2();
		//test3();
	}

	private static void test() throws CloneNotSupportedException {
		Date time=new Date(10);
		System.out.println("创建Date类型："+time);
		Person person=new Person();
		System.out.println("创建ancestor对象："+person);
		person.setTime(time);
		System.out.println("设置ancestor属性time为："+person.getTime());
		time.setTime(12);
		System.out.println("修改时间为："+time);
		System.out.println("查看ancestor属性time："+person.getTime());
		Person clone=(Person) person.clone();
		System.out.println("创建clone对象："+clone);
		System.out.println("查看clone属性time："+clone.getTime());
		time.setTime(1613156L);
		System.out.println("查看ancestor属性time："+person.getTime());
		System.out.println("查看clone属性time："+clone.getTime());
	}

	private static void test2() throws CloneNotSupportedException {
		Date time=new Date(156613L);
		System.out.println("创建Date类型："+time);
		Person2 person=new Person2();
		System.out.println("创建ancestor对象："+person);
		person.setTime(time);
		System.out.println("设置ancestor属性time为："+person.getTime());
		time.setTime(1151313L);
		System.out.println("修改时间为："+time);
		System.out.println("查看ancestor属性time："+person.getTime());
		Person2 clone=(Person2) person.clone();
		System.out.println("创建clone对象："+clone);
		System.out.println("查看clone属性time："+clone.getTime());
		time.setTime(1613156L);
		System.out.println("查看ancestor属性time："+person.getTime());
		System.out.println("查看clone属性time："+clone.getTime());
	}

	private static void test3() throws IOException, ClassNotFoundException {
		System.out.println("=========`===========");
		Date time=new Date(156613L);
		Person2 person=new Person2();
		person.setTime(time);
		System.out.println(person);
		System.out.println(person.getTime());
		System.out.println("--------修改时间1后----------");
		time.setTime(1151313L);
		System.out.println(person.getTime());
		System.out.println("--------序列化克隆----------");
		//Person person1=(Person) person.clone();
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(baos);
		oos.writeObject(person);
		byte[] bytes=baos.toByteArray();

		ByteArrayInputStream bais=new ByteArrayInputStream(bytes);
		ObjectInputStream ois=new ObjectInputStream(bais);
		Person2 person1 = (Person2) ois.readObject();

		System.out.println(person1);
		System.out.println(person1.getTime());
		System.out.println("--------修改时间2后----------");
		time.setTime(1613156L);
		System.out.println(person.getTime());
		System.out.println(person1.getTime());
	}

}
