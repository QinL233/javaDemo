package thread.concurrent.sycn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 17:54
 */
public class CustomSycn {
    /**
     * 通过自定义一个对象做sync的锁使用
     */
    private static final byte[] lock = new byte[0];

    private static final List<Integer> list = new ArrayList();

    private static int count = 0;

    public static void increase(){
        synchronized (lock){
            list.add(1);
        }
    }

    private static void increase1(){
        synchronized (lock){
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int size = 1000000;
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for(int i=0;i<size;i++){
            pool.execute(()->{
                increase();
                increase1();
                latch.countDown();
            });
        }
        latch.await();
        pool.shutdown();
        System.out.println(list.size());
        System.out.println(count);
    }
}
