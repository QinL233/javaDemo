package action;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令模式，注重将方法封装成对象传入执行器
 *
 * @author LiaoQinZhou
 * @date: 2021/2/25 10:36
 */
public class OrderDemo {
    public static void main(String[] args) {
        //自定义指定方法（将方法封装成对象）
        Order start = new Order() {
            @Override
            public void execute() {
                System.out.println("============== start =============");
            }
        };
        Order order = new Order() {
            @Override
            public void execute() {
                System.out.println(this.getClass().getName());
            }
        };
        Order end = new Order() {
            @Override
            public void execute() {
                System.out.println("============== end =============");
            }
        };
        //[重点]：将方法按对象传入执行器，可对多个方法定制执行过程，例如规划成aop，同步(单线程顺序)，异步(多线程)
        Handler handler = new Handler();
        handler.addOrder(start);
        handler.addOrder(order);
        handler.addOrder(end);
        //执行
        handler.start();
    }
}

/**
 * 1.定义一个命令接口
 */
interface Order {
    void execute();
}

/**
 * 2.定义一个处理器，添加指定命令，并循环执行
 */
class Handler {
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void start() {
        orders.forEach(Order::execute);
    }
}