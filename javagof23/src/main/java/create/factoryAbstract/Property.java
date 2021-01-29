package create.factoryAbstract;

public interface Property {
    default String addProperty(){
        return this.getClass().getSimpleName();
    }
}
