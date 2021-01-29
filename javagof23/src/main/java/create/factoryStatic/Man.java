package create.factoryStatic;

public class Man implements Person {
    @Override
    public void getName() {
        System.out.println("I am "+this.getClass().getName());
    }
}
