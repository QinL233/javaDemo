package service;

import model.Person;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.*;

/**
 * lambda包括简化内部类的语法操作，以及"::"方法引用构建"方法/接口实例"以便lambda方式操作
 */
public class LambdaTest {

    public Logger log=Logger.getLogger(LambdaTest.class);
    //本地的实例变量、静态变量，用于lambda实现
    private String localName;
    public Integer localAge;
    //用于::方法引用实现
    public static void printStr(String str){
        System.out.print(str+"\t");
    }

    //自定义list排序实现,使用lambda和::引用
    @Test
    public void test1(){
        log.info("创建list");
        List<String> list= Arrays.asList("tfa","act","dee");
        //循环打印
        for (String s:list){
            printStr(s);
        }
        //使用传统方法排序list
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        //使用forEach并结合lambda打印
        list.forEach(s -> printStr(s));
        /*
            使用lambda排序list
            (a,b): 编译器根据后续方法自动编译成 {String a,String b)
         */
        Collections.sort(list,(a,b) -> b.compareTo(a));
        //使用forEach并结合::方法引用打印
        list.forEach(LambdaTest::printStr);
    }

    //自定义lambda实现
    @Test
    public void test2(){
        //自定义类使用lambda实现
        Factor f1 = (String s, Integer a) -> new Person(s, a);
        log.info(f1.create("lqz",22));

        //使用lambda随意改造变量
        Factor f2 = (String s, Integer a) -> new Person(s+"lqz", a+11);
        log.info(f2.create("lqz",22));

        //lambda使用域与匿名内部类相似，在引用局部变量时，final成隐性：即不标记final也能通过编译，但不可以再更改变量值
        String str="lqj";
        //不可再次更改局部变量值，因此str的使用条件是隐性final
        //str="lqz";
        Integer age=21;
        //此处使用的是方法内部变量赋值，而非传递变量
        Factor f3 = (s, a) -> new Person(str,age);
        log.info(f3.create("lqz",22));

        //在引用实例变量和静态变量时，可以随意操作：与匿名内部类一致
        LambdaTest demo = new LambdaTest();
        demo.localName="李清照";
        demo.localName="李清照2";
        localAge = 33;
        localAge = 34;
        Factor f4 = (s, a) -> new Person(demo.localName,localAge);
        log.info(f4.create("lqz",22));
        //编译按前后顺序执行
        demo.localName="李清照3";
        localAge = 35;

        //匿名内部类可以访问default标识的默认方法
        DefaultMethodFactor f5 = new DefaultMethodFactor() {
            @Override
            public Person create(String s, Integer a) {
                return new Person(getName(),getAge());
            }
        };
        log.info(f5.create("lqz",22));

        //lambda无法访问default
        //Factor2 f6 = (s,a) -> new Person(getName(),getAge());
    }

    //自定义::关键字引用 >>>> 构建一个"接口"实例
    @Test
    public void test3(){
        //使用 :: 关键字来传递构造函数引用
        Factor<Person> f1 =Person::new;
        log.info(f1);
        log.info(Objects.isNull(f1));
        log.info(f1.create("one", 11));

        //使用 :: 关键字来传递方法引用
        Factor<Person> f2 =Person::create;
        log.info(f2);
        log.info(Objects.nonNull(f2));
        log.info(f2.create("two", 21));
    }
}
