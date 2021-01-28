package service;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class StreamTest {

    private static Logger log=Logger.getLogger(StreamTest.class);

    //Stream接口常用方法
    @Test
    public void stream(){
        List<Integer> list= Arrays.asList(1,3,2,5,4,6,8,7);
        //串行stream，按顺序执行
        //filter && forEach
        list.stream()                           //调用stream
                .filter(i -> i>3)               //filter需要调用predicate接口：做条件判断
                .forEach(l -> System.out.print(l));     //forEach需要调用consumer接口：做参数处理操作
        System.out.println();
        //sorted:生成新的list，默认升序,
        list.stream().sorted().forEach(l->System.out.print(l));
        System.out.println();
        list.stream().sorted((l1,l2)->l2.compareTo(l1)).forEach(l->System.out.print(l));
        System.out.println();
        //map:映射接口，将对象的值映射:将Integer转换成String并判断是否以3开头
        list.stream().map(String::valueOf).forEach(l->System.out.print(l.startsWith("3")+"\t"));
        System.out.println();
        //match匹配，匹配整个集合是否满足predicate条件，从而返回一个boolean
        System.out.println(list.stream().anyMatch(l -> l > 0));//任意一个满足
        System.out.println(list.stream().allMatch(l -> l > 0));//所有满足
        System.out.println(list.stream().noneMatch(l -> l > 0));//没有一个满足
        //count计数器
        System.out.println(list.stream().filter(l->l>5).count());
        //reduce，最终执行reduce内的规则并返回一个新的optional对象
        Optional<String> reduce = list.stream().filter(l -> l <= 5).map(String::valueOf).reduce((l1, l2) -> l1 + "##" + l2);
        System.out.println(reduce);

        //并行stream，多线程执行
        long start = System.nanoTime();
        int size=1000000;
        List<String> value =new ArrayList<>(size);
        for (int i=0;i<size;i++){
            value.add(UUID.randomUUID().toString());
        }
        System.out.println(String.format("add time: %d ms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start)));
        start=System.nanoTime();
        value.stream().sorted().count();
        System.out.println(String.format("1 sort time: %d ms",TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start)));
        start=System.nanoTime();
        value.parallelStream().sorted().count();
        System.out.println(String.format("2 sort time: %d ms",TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start)));

        //Map集合不支持stream，但又新方法使用
        Map<Integer,String> map=new HashMap<>();
        for(int j=0;j<10;j++){
            map.putIfAbsent(j,"No."+String.valueOf(j));     //putIfAbsent判断k是否存在
        }
        for(int j=0;j<10;j++){
            map.putIfAbsent(j,"No."+String.valueOf(j));
        }
        map.forEach((k,v)-> System.out.print(v+"\t"));      //forEach与stream相同
        System.out.println();
        map.compute(3, (k,v) -> v + "1");               //compute对指定k的值进行操作
        map.forEach((k,v)-> System.out.print(v+"\t"));
        System.out.println();
        map.computeIfAbsent(3, v -> v + "1");           //computeIfAbsent对指定k位置进行null判断，是null1才执行操作
        map.forEach((k,v)-> System.out.print(v+"\t"));
        System.out.println();
        System.out.println("remove:"+map.remove(3,"No.3"));         //remove同时匹配kv
        System.out.println("remove:"+map.remove(3,"No.31"));
        System.out.println(map.getOrDefault(3,"not found"));       //get给予默认值
        map.merge(3,"No.3",(o,n)->o.concat(n));                           //merge合并当前k的值
        System.out.println(map.getOrDefault(3,"not found"));
        map.merge(3,"No.3",(o,n)->o.concat(n));
        System.out.println(map.getOrDefault(3,"not found"));
    }


}
