package util;

import model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author LiaoQinZhou
 * @date: 2020/10/20 16:45
 */
public class StreamUtil {

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void main(String[] args) {
        List<Person> persons=new ArrayList<>();
        for (int i=0;i<10;i++){
            Person person = new Person();
            person.setAge(i);
            person.setName(i%2==0?"男":"女");
            persons.add(person);
        }
        System.out.println(persons.size());
        List<Person> list = persons.stream().filter(distinctByKey(Person::getName)).collect(Collectors.toList());
        System.out.println(list);
    }
}
