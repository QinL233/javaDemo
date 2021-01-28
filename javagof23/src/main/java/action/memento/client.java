package action.memento;

public class client {

	public static void main(String[] args) {
		/**
		 * 1.在用户进行操作的时候（发起人），对（原型对象）的值做任意更改或操作
		 * 2.发起人通过方法把需要保存的数据（备忘录）给入管理
		 * 3.在之后任意操作后，发起人可以获取管理已经保存的（备忘录）回滚数据
		 */
		发起人 a=new 发起人();
		管理 b=new 管理();
		a.setState("111");
		System.out.println(a.getState());
		System.out.println("创建备忘录");
		b.set(a.create());
		a.setState("222");
		System.out.println(a.getState());
		a.setState("333");
		System.out.println(a.getState());
		System.out.println("回滚");
		a.record(b.get());
		System.out.println(a.getState());
	}
}
