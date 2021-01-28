package create.builder;

public class GoodManBuilder implements Builder {
    private Person person= Person.getPerson();

    @Override
    public void setSex() {
        person.setSex("Man");
    }

    @Override
    public void setProperty() {
        person.setProperty("Good");
    }

    @Override
    public Person getPerson() {
        return person;
    }
}
