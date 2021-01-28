package model;


/**
 * 定义一个简单得到对象
 */
public class Person {
    private String name;
    private Integer age;
    //桥套D
    private Person Dad;

    public Person(){};

    public Person(String name, Integer age){
        this.name=name;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person getDad() {
        return Dad;
    }

    public void setDad(Person dad) {
        Dad = dad;
    }

    public static Person create(String name, Integer age){
        return new Person(name,age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}