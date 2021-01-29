package create.factoryStatic;

public class Woman implements Person {
    @Override
    public void getName() {
        System.out.println("I am "+this.getClass().getName());
    }
}
