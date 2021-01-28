package create.abstractFactory;

public interface Property {
    default String addProperty(){
        return this.getClass().getSimpleName();
    }
}
