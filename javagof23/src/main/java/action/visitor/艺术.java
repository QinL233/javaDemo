package action.visitor;

public class 艺术 implements 访问者 {

	@Override
	public void 访问(纸 a) {
		System.out.println(a.name+"变画");
	}

	@Override
	public void 访问(铜 a) {
		System.out.println(a.name+"变铜像");
	}

}
