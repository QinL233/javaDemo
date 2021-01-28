package create.builder;

public class BadManBuilder implements Builder {
    private Person person=Person.getPerson();

    @Override
    public void setSex() {
        person.setSex("Man");
    }

    @Override
    public void setProperty() {
        person.setProperty("Bad");
    }

    @Override
    public Person getPerson() {
        return person;
    }
}
