package service;

import model.Person;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 函数式接口:能将将方法/函数定义成对象/参数形式传递
 * 1.Predicate<t> 传入t返回boolean
 * 2.Function<k,v> 传入k,v但仅可返回v
 * 3.Supplier<t> 传入t返回t
 * 4.Consumer<t> 传入t无返回
 * 5.Comparator<t> 传入t返回int
 */
public class ImplementTest {
    public Logger log = Logger.getLogger(ImplementTest.class);

    //Predicate 对象是一个返回boolean类型的方法，其泛型既为参数
    @Test
    public void predicate() {
        //t作为形参传入方法中
        Predicate<Integer> predicate = (t) -> t > 10;
        //使用test可以测试结果
        log.info(predicate.test(10));
        log.info(predicate.test(11));
        //negate 反转
        log.info(predicate.negate().test(11));

        //使用“::”做方法传递，此时isNul = (t) - > Object.isNull(t)
        Predicate<Integer> isNull = Objects::isNull;
        Predicate<Integer> nonNull = Objects::nonNull;
        Predicate<String> isEmpty = String::isEmpty;
        //方法直接传递时必须返回Predicate对象
        Predicate<String> isNotEmpty = isEmpty.negate();

        log.info("isnull:" + isNull.test(null));
        log.info("nonNull:" + nonNull.test(null));
        log.info("isEmpty:" + isEmpty.test("1"));
        log.info("isNotEmpty:" + isNotEmpty.test("1"));
    }

    //Function 接口: 前一个参数为形参，后一个为返回参数，常用于转型,可以拼接
    @Test
    public void function() {
        //既k 通过 function 必须等于 y
        Function<String, Integer> toInt = Integer::valueOf;
        Function<Integer, String> toStr = String::valueOf;
        log.info(toInt.apply("123").getClass());
        log.info(toStr.apply(123).getClass());
        //嵌套多重转换
        Function<String, Double> toIntAndToDouble = toInt.andThen(Double::valueOf);
        log.info(toIntAndToDouble.apply("456").getClass());
    }

    //Supplier 无参数传递，仅执行方法，常用于获取实例
    @Test
    public void supplier() {
        Supplier<Person> supplier = Person::new;
        log.info(supplier.get());
    }

    //Consumer 帮助对参数进行操作
    @Test
    public void consumer() {
        //1.使用 Consumer 定制操作
        Consumer<Person> consumer = (p) -> log.info(p.toString());
        //使用工厂创建实例
        Factor1 factor1 = (s, a) -> new Person(s, a);
        Person p1 = factor1.create("李白", 12);
        //放入实例执行操作
        consumer.accept(p1);

        //2.使用 Consumer 定制操作
        Consumer<Person> consumer2 = (p) -> {
            p.setName("杜甫");
            p.setAge(21);
            log.info(p.toString());
        };
        //使用 supplier 重新创建一个实例
        Supplier<Person> supplier = Person::new;
        //执行
        consumer2.accept(supplier.get());
    }

    //Comparator 帮助参数进行比较/排序等规则的定制，定义两个形参，定义计算返回一个int
    @Test
    public void comparator() {
        //1.设置按年龄比较，前者大返回x，y，相等返回z。java中默认Comparator时为1,-1,0
        Comparator<Person> comparator = (p1, p2) -> p1.getAge()> p2.getAge()?1:p1.getAge()==p2.getAge()?-1:0;
        Consumer<Person> consumer;
        Supplier<Person> supplier = Person::new;
        //使用supplier创建对象
        Person p1 = supplier.get();
        Person p2 = supplier.get();
        //使用consumer操作对象
        consumer = (p) -> {
            p.setName("libao");
            p.setAge(22);
        };
        consumer.accept(p1);
        consumer = (p) -> {
            p.setName("dufu");
            p.setAge(21);
        };
        consumer.accept(p2);
        log.info("前者大于后者"+comparator.compare(p1, p2));
        log.info("后者大于前者"+comparator.compare(p2, p1));
        log.info("使用reversed反转结果"+comparator.reversed().compare(p2, p1));
        log.info("两者相等"+comparator.compare(p2, p2));

    }

}
