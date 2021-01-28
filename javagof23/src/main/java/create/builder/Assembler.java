package create.builder;

public class Assembler {
	private Builder builder=null;
	
	public Assembler(Builder builder) {
		this.builder = builder;
	}

	public Person assemble() {
		builder.setSex();
		builder.setProperty();
		return builder.getPerson();
	}
}
