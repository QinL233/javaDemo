package action.chain;

public class First extends Chain {

	public First(boolean ok) {
		super(ok);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doSomething() {
		System.out.println(getClass().getName()+"为你解决问题");
	}

}
