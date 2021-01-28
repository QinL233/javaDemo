package action.visitor;

public class client {

	public static void main(String[] args) {
		//通过角色入口
		角色 n=new 角色();
		//把已有原型
		n.add(new 纸());
		n.add(new 铜());
		//通过不同的访问者
		访问者 m=new 商业();
		n.访问(m);
		//实现不同的结果
		System.out.println("============换个对象=========");
		访问者 P=new 艺术();
		n.访问(P);
	}

}
