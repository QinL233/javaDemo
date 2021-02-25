package action;

import java.util.Objects;

/**
 * 责任链模式，注重处理器(对象)执行时[成链路形式]运行
 * @author LiaoQinZhou
 * @date: 2021/2/25 10:06
 */
public class ChainDemo {
    public static void main(String[] args) {
        //具体链路的实现
        AbstractChain oneChain = new AbstractChain() {
            @Override
            boolean execute(int i) {
                if(i < 30){
                    System.out.println(this.getClass().getName() + " handle success");
                    return true;
                }
                System.out.println(this.getClass().getName() + " handle failure");
                return false;
            }
        };
        AbstractChain twoChain = new AbstractChain() {
            @Override
            boolean execute(int i) {
                if(i < 20){
                    System.out.println(this.getClass().getName() + " handle success");
                    return true;
                }
                System.out.println(this.getClass().getName() + " handle failure");
                return false;
            }
        };
        //[重点]：串联链路，可以将方法呈现链表形式执行，例如实现拦截器作用
        oneChain.setNextChain(twoChain);
        //执行
        oneChain.start(21);
    }
}

/**
 * 1.定义抽象点
 */
abstract class AbstractChain{

    /**
     * 2.下一个链路
     */
    private AbstractChain nextChain;

    public void setNextChain(AbstractChain nextChain) {
        this.nextChain = nextChain;
    }

    /**
     * 3.具体方法返回执行成功or失败
     * @param i
     * @return
     */
    abstract boolean execute(int i);

    /**
     * 4.当前点的处理器，执行失败则运行下一个链路点的处理器
     */
    public void start(int i){
        if(!execute(i) && Objects.nonNull(nextChain)){
            nextChain.start(i);
        }
    }
}