package service;

import model.Person;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ImplementTest {
    public Logger log=Logger.getLogger(ImplementTest.class);
    //Predicate 提供一个参数与boolean类型返回值
    @Test
    public void predicate(){
        Predicate<Integer> predicate=(i) -> i>10;

        log.info(predicate.test(10));
        log.info(predicate.test(11));
        log.info(predicate.negate().test(11));

        Predicate<Integer> isNull= Objects::isNull;
        Predicate<Integer> nonNull= Objects::nonNull;
        Predicate<String> isEmpty=String::isEmpty;
        Predicate<String> isNotEmpty =isEmpty.negate();
        log.info("isnull:"+isNull.test(null));
        log.info("nonNull:"+nonNull.test(null));
        log.info("isEmpty:"+isEmpty.test("1"));
        log.info("isNotEmpty:"+isNotEmpty.test("1"));
    }

    //Function接口: 前一个参数为apply参数，后一个为返回参数，常用于转型,可以拼接
    @Test
    public void function(){
        Function<String,Integer> toInt=Integer::valueOf;
        Function<Integer,String> toStr=String::valueOf;
        log.info(toInt.apply("123").getClass());
        log.info(toStr.apply(123).getClass());
        Function<String, Double> toIntAndToDouble = toInt.andThen(Double::valueOf);
        log.info(toIntAndToDouble.apply("456").getClass());
    }

    //Supplier无参数传递，仅返回实例
    @Test
    public void supplier(){
        Supplier<Person> supplier= Person::new;
        log.info(supplier.get());
    }
    //Consumer帮助对参数进行操作
    @Test
    public void consumer(){
        Consumer<Person> consumer = (p)-> log.info(p.toString());
        Supplier<Person> supplier=Person::new;
        Factor1 factor1=(s, a)->new Person(s,a);
        consumer.accept(factor1.create("lqz",12));
        consumer=(p)->{
            p.setName("lqj");
            p.setAge(21);
            log.info(p.toString());
        };
        consumer.accept(supplier.get());
    }
    //Comparator帮助参数进行比较排序
    @Test
    public void comparator(){
        Comparator<Person> comparator=(p1, p2)->p1.getAge().compareTo(p2.getAge());
        Consumer<Person> consumer;
        Supplier<Person> supplier=Person::new;
        //使用supplier创建对象
        Person p1=supplier.get();
        Person p2=supplier.get();
        //使用consumer操作对象
        consumer=(p)->{
            p.setName("lqz");
            p.setAge(22);
        };
        consumer.accept(p1);
        consumer=(p)->{
            p.setName("lqj");
            p.setAge(21);
        };
        consumer.accept(p2);
        log.info(comparator.compare(p1,p2));
        log.info(comparator.compare(p2,p1));
        log.info(comparator.reversed().compare(p2,p1));
        log.info(comparator.compare(p2,p2));

    }
    //Optional函数当作接口使用，避免空指针错误
    @Test
    public void optional(){
        Supplier<Person> supplier=Person::new;
        Consumer<Person> consumer;
        //Optional<Person> optional = Optional.of(supplier.get());
        Optional<Person> optional = Optional.ofNullable(supplier.get());
        //of与ofNullable不同之处在于能否传入null参数
        Optional<Person> optional1 = Optional.ofNullable( null);
        //判断是否存在值
        log.info(optional.isPresent());
        log.info(optional1.isPresent());
        //直接获取值
        log.info(optional.get());
        //如果有值则输出该值，否则输出参数
        log.info(optional.orElse(supplier.get()));
        log.info(optional1.orElse(supplier.get()));
        //创建lqz实例
        Person lqz=supplier.get();
        consumer=((p)->{
            p.setName("lqz");
            p.setAge(21);
        });
        consumer.accept(lqz);
        //创建ljw实例
        Person ljw=supplier.get();
        consumer=((p)->{
            p.setName("ljw");
            p.setAge(45);
        });
        consumer.accept(ljw);
        //一般方式获取dad，null抛异常后无法有效继续执行
        if(lqz != null){
            if(lqz.getDad()!=null){
                log.info(lqz.getDad().getName());
            }else
                log.info("dad is empty");
        }else
            log.info("person is empty");
        //使用optional获取dad，null不会抛出异常
        log.info(optional.ofNullable(lqz)
                .map(p->p.getDad())
                .orElse(supplier.get())
                .getName());
        //添加dad
        consumer=((p)->{
            p.setDad(ljw);
        });
        consumer.accept(lqz);
        log.info(lqz.getDad().getName());
        log.info(optional.ofNullable(lqz)
                .map(p->p.getDad())
                .orElse(supplier.get())
                .getName());
    }
}
