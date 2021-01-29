package create.factoryAbstract;

public interface Person{
	default void getName(){
		System.out.println(this.getClass().getSimpleName());
	}

	default void addProperty(String name){
		String str = this.getClass().getPackage().toString().replaceAll("package", "").trim() + "." + name;
		try {
			Property property = (Property) Class.forName(str).newInstance();
			System.out.print(property.addProperty()+" ");
			getName();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
