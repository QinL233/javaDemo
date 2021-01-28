package action.iterator;

public class client {
	public static void main(String[] args) {
		抽象聚合 a=new 聚合();
		a.add("aa");
		a.add("bb");
		a.add("cc");
		System.out.println("list:");
		抽象迭代器 it = a.get();
		while(it.hasNext()) {
			Object obj=it.next();
			System.out.println(obj.toString());
		}
		Object ob=it.first();
		System.out.println("first:"+ob.toString());
	}
}
