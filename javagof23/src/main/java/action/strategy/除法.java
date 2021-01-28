package action.strategy;

public class 除法 implements 抽象 {

	@Override
	public void 功能(int a,int b) {
		if(b!=0)
			System.out.println((double)a/b);
		else
			System.out.println("除数不得为零");
	}

}
