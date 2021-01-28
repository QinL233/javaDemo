package action.visitor;

public class 商业 implements 访问者 {

	@Override
	public void 访问(纸 a) {
		System.out.println(a.name+"变钞票");
	}

	@Override
	public void 访问(铜 a) {
		System.out.println(a.name+"变铜币");
	}

}
