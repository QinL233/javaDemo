package thread.concurrent.sycn;

import java.util.concurrent.CountDownLatch;

/**
 * 锁实例方法
 */
public class MethodSycn {

    public int count = 0;

    public static int staticCount = 0;

    public synchronized void increase1() {
        count++;
    }

    /**
     * 实例锁，只锁当前实例
     * 当前实例的此方法【不包括内部元素】满足线程安全,相当于synchronized(this)
     * 既synchronized与static synchronized能同时进行操作，其内部的共享元素不安全
     */
    public synchronized void increase2() {
        staticCount++;
    }

    /**
     * 类锁，所有实例都共享该锁
     * static方法能[避过]普通锁访问static属性，造成static元素线程不安全
     */
    public static synchronized void increase3() {
        staticCount++;
    }

    /**
     * 锁方法时，对方法内部的属性都适用，包括静态(全局/共享)和非静态属性
     *
     * @param args
     */
    public static void main(String[] args) {
        MethodSycn methodSycn = new MethodSycn();
        //正常属性
        int thread_size = 4;
        final CountDownLatch latch = new CountDownLatch(thread_size);
        for (int i = 0; i < thread_size; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000000; j++) {
//                    methodSycn.increase1();
                }
                latch.countDown();
            }).start();
        }
        //静态属性
        int thread_size2 = 4;
        final CountDownLatch latch2 = new CountDownLatch(thread_size2);
        for (int i = 0; i < thread_size2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000000; j++) {
//                    methodSycn.increase2();
                }
                latch2.countDown();
            }).start();
        }
        //静态方法
        int thread_size3 = 4;
        final CountDownLatch latch3 = new CountDownLatch(thread_size3);
        for (int i = 0; i < thread_size3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000000; j++) {
                    methodSycn.increase3();
                }
                latch3.countDown();
            }).start();
        }
        try {
            latch.await();
            latch2.await();
            latch3.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(methodSycn.count);
        System.out.println(methodSycn.staticCount);

    }

}
