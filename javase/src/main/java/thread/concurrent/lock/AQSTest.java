package thread.concurrent.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * sync和aqs的对比和使用
 *
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月02日 09:38:00
 */
public class AQSTest {

    public static void main(String[] args) {
        int size = 1000000;
        loop(size);
        loopWithSync(size);
        loopWithLock(size);
        loopWithSemaphore(size);
        loopWithCyclicBarrier(size);
        System.exit(0);
    }

    /**
     * 循环n次执行并统计时间和成功次数（无锁）
     *
     * @param size
     */
    private static void loop(Integer size) {
        new ListLoopHandler(Thread.currentThread().getStackTrace()[1].getMethodName(), size, (i) -> i.add("test"));
    }

    /**
     * 使用常规sync
     *
     * @param size
     */
    private static void loopWithSync(Integer size) {
        new ListLoopHandler(Thread.currentThread().getStackTrace()[1].getMethodName(), size, (i) -> {
            synchronized (i) {
                i.add("test");
            }
        });
    }

    /**
     * ReentrantLock具备sync所有特性，1.5/6/7之后sync升级，其性能不再对比考虑，只在需要特性时使用：
     * 1、可中断、lock.lockInterruptibly()表示放弃等待
     * 2、公平锁、线程队列保证队列公平
     * 3、可选择性通知其它线程、通过Condition实例可以仅唤醒注册在该Condition上的线程
     * 其中ReentrantLock和condition的组合应用催生其它CountDownLatch、CyclicBarrier等工具
     *
     * @param size
     */
    private static void loopWithLock(Integer size) {
        //初始化是否公平锁 - 公平锁会带来额外开销
        ReentrantLock lock = new ReentrantLock(false);
        new ListLoopHandler(Thread.currentThread().getStackTrace()[1].getMethodName(), size, (i) -> {
            lock.lock();
            try {
                i.add("Test");
            } finally {
                lock.unlock();
            }
        });
    }

    /**
     * 信号量，能以资源的角度出发，最多允许n个线程访问该资源，使用完必须释放信号
     * 设置为1的时候能当作lock使用
     *
     * @param size
     */
    private static void loopWithSemaphore(Integer size) {
        //一次一个坑、是否公平
        Semaphore semaphore = new Semaphore(1, false);
        new ListLoopHandler(Thread.currentThread().getStackTrace()[1].getMethodName(), size, (i) -> {
            try {
                semaphore.acquire();
                i.add("Test");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        });
    }

    /**
     * 栅栏 - 以线程的角度，能控制n个线程同时执行
     * await触发计数器n+1并阻塞线程，当n达到阈值则唤醒线程（同时执行）
     *
     * @param size
     */
    private static void loopWithCyclicBarrier(Integer size) {
        AtomicInteger count = new AtomicInteger(0);
        //每两次执行触发一次runnable
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            count.incrementAndGet();
        });
        new ListLoopHandler(Thread.currentThread().getStackTrace()[1].getMethodName(), size, (i) -> {
            try {
                //计数器+1
                barrier.await();
                //唤醒线程
                synchronized (i) {
                    i.add("Test");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        System.out.println(String.format("CyclicBarrier count size >>> %d", count.get()));
    }
}
