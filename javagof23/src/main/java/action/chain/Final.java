package action.chain;

public class Final extends Chain {

	public Final(boolean ok) {
		super(ok);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doSomething() {
		System.out.println(getClass().getName()+"为你解决问题");
	}

}
