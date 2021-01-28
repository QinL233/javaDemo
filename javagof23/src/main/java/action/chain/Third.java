package action.chain;

public class Third extends Chain {

	public Third(boolean ok) {
		super(ok);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doSomething() {
		System.out.println(getClass().getName()+"为你解决问题");
	}

}
