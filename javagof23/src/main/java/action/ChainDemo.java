package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 责任链模式：将[方法对象]通过chainHandler进行串联，实现我们对方法对象呈链表形式运行
 * @author LiaoQinZhou
 * @date: 2021/2/25 10:06
 */
public class ChainDemo {
    public static void main(String[] args) {
//        //具体链路的实现
//        ChainHandler handler1 = new ChainHandler() {
//            @Override
//            public boolean execute(int i) {
//                if(i < 10){
//                    System.out.println(this.getClass().getName() + " handle success");
//                    return true;
//                }
//                System.out.println(this.getClass().getName() + " handle failure");
//                return false;
//            }
//        };
//        ChainHandler handler2 = new ChainHandler() {
//            @Override
//            public boolean execute(int i) {
//                if(i < 20){
//                    System.out.println(this.getClass().getName() + " handle success");
//                    return true;
//                }
//                System.out.println(this.getClass().getName() + " handle failure");
//                return false;
//            }
//        };
//        //[重点]：串联链路，可以将方法呈现链表形式执行，例如实现拦截器作用
//        handler1.setNextChain(handler2);
//        //执行
//        System.out.println("=========================");
//        handler1.start(9);
//        System.out.println("=========================");
//        handler1.start(11);
//        System.out.println("=========================");
//        handler1.start(21);
//        System.out.println("=========================");

        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        ChainHandler startHandler = new ChainHandler() {
            @Override
            public boolean execute(int i) {
                System.out.println(i);
                return true;
            }
        };

        ChainHandler lastHandler = startHandler.getNextChain();
        for(int i =0;i<list.size();i++){
            ChainHandler handler = new ChainHandler() {
                @Override
                public boolean execute(int i) {
                    System.out.println(i);
                    return true;
                }
            };
            if(Objects.nonNull(lastHandler)){
                lastHandler.setNextChain(handler);
            }
            lastHandler = handler;
        }
        System.out.println(startHandler);
        startHandler.start(1);
    }
}

/**
 * 1.将方法/处理逻辑定制为一段chain
 */
interface Chain{
    boolean execute(int i);
}
/**
 * 2.定制chain处理器
 */
abstract class ChainHandler implements Chain{
    /**
     * 1.下一个链路
     */
    private ChainHandler nextChain;

    public ChainHandler getNextChain() {
        return nextChain;
    }

    public void setNextChain(ChainHandler nextChain) {
        this.nextChain = nextChain;
    }

    /**
     * 2.当前点的处理器，执行失败则运行下一个链路点的处理器
     */
    public void start(int i){
        if(!execute(i) && Objects.nonNull(nextChain)){
            nextChain.start(i);
        }
    }
}