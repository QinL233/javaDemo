package action.interpreter;

public class 环境 {

	private String[] a= {"广州","深圳"};
	private String[] b= {"老人","妇女","儿童"};
	private 表达式 m;
	public 环境() {
		表达式 city=new 终结符(a);
		表达式 people=new 终结符(b);
		m=new 非终结符(city,people);
	}
	
	public void 解析(String info) {
		boolean ok=m.解释(info);
		System.out.print(info);
		if(ok) {
			System.out.println("符合！！");
		}else {
			System.out.println("不符合");
		}
	}
}
