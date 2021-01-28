package thread.pool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomPool {
    public static void main(String[] args) {
        System.out.println("=====begin=====");
        int MAX_SIZE = 10;
        CountDownLatch count = new CountDownLatch(MAX_SIZE);
        /* 使用ThreadPoolExecutor自定义线程池，参数
        corePoolSize - 池中所保存的线程数，包括空闲线程，必须大于或等于0。
        maximumPoolSize - 池中允许的最大线程数，必须大于或等于corePoolSize。
        keepAliveTime - 线程存活时间，当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
        unit - keepAliveTime 参数的时间单位，必须大于或等于0。
        【workQueue】 - 工作队列，执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的 Runnable 任务。
        threadFactory - 执行程序创建新线程时使用的工厂，默认为DefaultThreadFactory类。
        【handler】 - 拒绝策略，由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序，默认策略为ThreadPoolExecutor.AbortPolicy。
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 10L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1000), new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("number" + finalI + "-->" + Thread.currentThread().getName() + " running");
                count.countDown();
            });
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        System.out.println("====end=====");
    }

    //阻塞队列
    private void workQueue() {

    }

    //拒绝策略
    private void handle() {
        /*
            AbortPolicy	抛出RejectedExecutionException
            DiscardPolicy	什么也不做，直接忽略
            DiscardOldestPolicy	丢弃执行队列中最老的任务，尝试为当前提交的任务腾出位置
            CallerRunsPolicy	直接由提交任务者执行这个任务
         */
        new ThreadPoolExecutor.AbortPolicy();
    }
}
