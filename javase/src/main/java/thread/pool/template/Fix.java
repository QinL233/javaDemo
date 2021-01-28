package thread.pool.template;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fix {
    public static void main(String[] args) {
        System.out.println("=====begin=====");
        //创建线程池
        final int MAX_SIZE = 10;
        ExecutorService pool = Executors.newFixedThreadPool(MAX_SIZE / 2);
        //线程计数器(10个)
        CountDownLatch count = new CountDownLatch(MAX_SIZE);
        //使用execute执行线程
        for (int i = 0; i < MAX_SIZE; i++) {
            pool.execute(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " running by fix");
                //计数器-1
                count.countDown();
            });
        }
        try {
            //等待线程全部执行完毕
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //关闭线程池
        pool.shutdown();
        System.out.println("=====end=====");
    }
}
