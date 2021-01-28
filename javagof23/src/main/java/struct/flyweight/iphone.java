package struct.flyweight;

public class iphone implements phone {

	private String name;
	
	public iphone(String name){
		this.name=name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	@Override
	public void name() {
		System.out.println("i am "+name);
	}

	@Override
	public void function(String function) {
		System.out.println("i can "+function);
	}

}
