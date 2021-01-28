package thread.concurrent.local;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 18:02
 */
public class ThreadLocalTest {

    /**
     * threadLocal设置的值仅在当前线程内有效
     */
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        threadLocal.set(0);
        Thread a = new Thread(() -> {
            threadLocal.set(1);
            System.out.println(Thread.currentThread().getName()+" >>> "+threadLocal.get());
            latch.countDown();
        });
        Thread b = new Thread(() -> {
            threadLocal.set(2);
            System.out.println(Thread.currentThread().getName()+" >>> "+threadLocal.get());
            latch.countDown();
        });
        a.start();
        b.start();
        latch.await();
        System.out.println(Thread.currentThread().getName()+" >>> "+threadLocal.get());
    }
}

