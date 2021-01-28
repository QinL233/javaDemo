package action.interpreter;

public class 非终结符 implements 表达式{

	private 表达式 a= null;
	private 表达式 b= null;
	public 非终结符(表达式 a,表达式 b) {
		this.a=a;
		this.b=b;
	}
	@Override
	public boolean 解释(String str) {
		String s[]=str.split("的");
		return a.解释(s[0])&&b.解释(s[1]);
	}
}
