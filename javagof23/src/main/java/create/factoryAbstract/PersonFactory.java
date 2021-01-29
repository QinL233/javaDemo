package create.factoryAbstract;

public class PersonFactory implements Factory {
    @Override
    public Person create(String name) {
        String str = this.getClass().getPackage().toString().replaceAll("package", "").trim() + "." + name;
        try {
           return (Person) Class.forName(str).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
