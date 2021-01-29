package service;


import model.Person;

/**
 * 1.使用default标记默认方法
 * @param <p>
 */
public interface Factor2<p extends Person> {
    p create(String s, Integer a);

    default String getName(){
        return "name";
    }
    default Integer getAge(){
        return 0;
    }
}
