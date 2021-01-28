package thread.status;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:43
 */
public class Yield {
    /**
     * 执行 yield[让出]方法的线程将转为（运行态）等待cpu重写调度，也就是start后的状态
     * 注：不会释放锁资源
     * @param args
     */
    public static void main(String[] args) {
        final AtomicInteger i = new AtomicInteger(0);
        Thread demo1 = new Thread(() -> {
            for(; i.get() < 10; i.incrementAndGet()){
                Thread.yield();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" >>> "+ i);
            }
        },"A");
        Thread demo2 = new Thread(() -> {
            for(; i.get() < 10; i.incrementAndGet()){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" >>> "+ i);
            }
        }, "B");
        demo1.start();
        demo2.start();
    }
}
