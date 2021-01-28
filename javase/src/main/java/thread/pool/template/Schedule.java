package thread.pool.template;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedule {
    public static void main(String[] args) {
        System.out.println("=====begin=====");
        //创建线程池
        final int MAX_SIZE=10;
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(MAX_SIZE / 3);
        //线程计数器(10个)
        CountDownLatch count=new CountDownLatch(MAX_SIZE);
        //使用schedule执行线程
        for (int i=0;i<MAX_SIZE;i++) {
            //定时线程，一秒后执行
            pool.schedule(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " running by schedule");
                //计数器-1
                count.countDown();
            },1, TimeUnit.SECONDS);
            //循环周期线程，2秒后，两次任务间隔1秒执行
            pool.scheduleAtFixedRate(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " running by scheduleAtFix");
                count.countDown();
            },2,1,TimeUnit.SECONDS);
        }
        try {
            //等待线程全部执行完毕
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //关闭线程池
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        System.out.println("=====end=====");
    }
}
