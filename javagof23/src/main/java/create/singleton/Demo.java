package create.singleton;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年02月21日 15:16:00
 */
public class Demo {

    public static void main(String[] args) {
        /*
            创建型 - 单例模式
            使用一个私有构造函数、一个私有静态变量以及一个公有静态函数来实现。
            私有构造函数保证了不能通过构造函数来创建对象实例，只能通过公有静态函数返回唯一的私有静态变量。
         */

        /*
            1、饿汉模式
            初始化就创建实例，适用于初始速度快、占用内存小的场景
         */
        Hungry hungry1 = Hungry.getSingleton();
        Hungry hungry2 = Hungry.getSingleton();
        System.out.println(hungry1.getClass() == hungry2.getClass());

        HungryByEnum hungryByEnum1 = HungryByEnum.singletion;
        HungryByEnum hungryByEnum2 = HungryByEnum.singletion;
        System.out.println(hungryByEnum1.getClass() == hungryByEnum2.getClass());
        /*
            2、懒汉模式
            按需延迟创建单例，适用于初始耗时长&启动慢、内存大
         */


    }
}
