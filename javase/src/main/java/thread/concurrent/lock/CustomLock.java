package thread.concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomLock {

    private static List<Byte> list = new ArrayList<>();

    private static int count = 0;

    private static Lock lock = new ReentrantLock();

    /**
     * 通过自定lock实现list操作时线程安全
     */
    public static void increase(){
        lock.lock();
        try {
            list.add((byte)0);
        }finally {
            lock.unlock();
        }
    }

    public static void increase2(){
        list.add((byte)0);
    }

    public static void increase3(){
        lock.lock();
        try {
            count++;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        int count = 1000000;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i=0;i<count;i++){
            pool.execute(()->{
                increase();
                increase3();
                latch.countDown();
            });
        }
        latch.await();
        pool.shutdown();
        System.out.println(list.size());
        System.out.println(count);
    }
}
