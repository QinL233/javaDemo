package thread.status;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:36
 */
public class Join {
    /**
     * 使用子线程使用join，该线程会优先运行完成，其它线程在其运行时为阻塞态
     * join：[抢占]资源执行，其它线程进入阻塞，并且其它线程会在该线程执行完后执行
     * 注：不会抢占锁资源
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger i = new AtomicInteger(0);
        System.out.println(Thread.currentThread().getName()+"主线程开始");
        synchronized (i){
            for (; i.get() < 5; i.incrementAndGet()) {
                Thread.sleep(1000);
            }
        }
        Thread demo1 = new Thread(() -> {
            for (; i.get() < 5; i.incrementAndGet()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }, "A");
        Thread demo2=new Thread(()->{
            for (; i.get() < 5; i.incrementAndGet()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        },"B");
        demo1.start();
        demo2.start();
        try {
            demo1.join();
            demo2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"主结束开始");
    }
}
