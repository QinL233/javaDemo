package service;

import model.Person;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Optional<t> 包装t解决t的空指针问题
 * @author LiaoQinZhou
 * @date: 2021/1/29 10:31
 */
public class OptionalTest {
    public Logger log = Logger.getLogger(OptionalTest.class);

    //Optional 函数当作接口使用，可以优雅的避免空指针错误，方法时构建一个永远不会null的对象包装"目标对象"
    @Test
    public void optional() {
        Supplier<Person> supplier = Person::new;
        Consumer<Person> consumer;
        //Optional<Person> optional = Optional.of(supplier.get());
        Optional<Person> optional = Optional.ofNullable(supplier.get());
        //of与ofNullable不同之处在于能否传入null参数
        Optional<Person> optional1 = Optional.ofNullable(null);
        //判断是否存在值
        log.info(optional.isPresent());
        log.info(optional1.isPresent());
        //直接获取值
        log.info(optional.get());
        //如果有值则输出该值，否则输出参数
        log.info(optional.orElse(supplier.get()));
        log.info(optional1.orElse(supplier.get()));
        //创建lqz实例
        Person libai = supplier.get();
        consumer = ((p) -> {
            p.setName("libai");
            p.setAge(21);
        });
        consumer.accept(libai);
        //创建ljw实例
        Person dufu = supplier.get();
        consumer = ((p) -> {
            p.setName("dufu");
            p.setAge(45);
        });
        consumer.accept(dufu);
        //一般方式获取dad，null抛异常后无法有效继续执行
        if (libai != null) {
            if (libai.getDad() != null) {
                log.info(libai.getDad().getName());
            } else
                log.info("libai is empty");
        } else
            log.info("person is empty");
        //使用optional获取dad，null不会抛出异常
        log.info(optional.ofNullable(libai)
                .map(p -> p.getDad())
                .orElse(supplier.get())
                .getName());
        //添加dad
        consumer = ((p) -> {
            p.setDad(dufu);
        });
        consumer.accept(libai);
        log.info(libai.getDad().getName());
        log.info(optional.ofNullable(libai)
                .map(p -> p.getDad())
                .orElse(supplier.get())
                .getName());
    }
}
