package service;


import model.Person;

/**
 *  定义只有一个方法的接口，支持使用lambda表达式
 * @FunctionalInterface 注解标识的接口只能有一个方法否则报错
 * @param <p>
 */
@FunctionalInterface
public interface Factor1<p extends Person> {

    p create(String s, Integer a);
}
