package struct.bridge;

public interface 品牌 {

	public void name();
}

class 华为 implements 品牌 {

	@Override
	public void name() {
		System.out.println("华为");
	}
	
}

class 联想 implements 品牌 {

	@Override
	public void name() {
		System.out.println("联想");
	}
	
}
