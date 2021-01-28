package create.builder;

public class Person {

    private static Person person;

    private String sex;
    private String property;

    private Person(){
        if(person != null){
            throw new IllegalArgumentException("err");
        }
    }

    public static Person getPerson() {
        return person != null? person :(person=new Person());
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
