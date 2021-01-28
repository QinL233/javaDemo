package thread.concurrent.sycn;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 13:41
 */
public class ChunkSycn {

    public Integer count = 0;

    public static Integer staticCount = 0;

    public void increase(){
        /*
            可以将当前类锁住,实现类内部所有静态/非静态属性线程安全
         */
        synchronized (this){
            count++;
        }
    }

    public void increase1(){
        synchronized (this){
            staticCount++;
        }
    }
    public void increase2(ChunkSycn chunkSycn){
        /*
            锁静态属性无效，必须锁实例
         */
        synchronized (chunkSycn){
            chunkSycn.count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChunkSycn chunkSycn = new ChunkSycn();
        int size = 10;
        ExecutorService pool = Executors.newFixedThreadPool(size);
        CountDownLatch latch = new CountDownLatch(size);
        for (int i=0;i<size;i++){
            pool.execute(()->{
                for (int j =0;j<10000000;j++){
//                    chunkSycn.increase();
                }
                latch.countDown();
            });
        }
        ExecutorService pool1 = Executors.newFixedThreadPool(size);
        CountDownLatch latch1 = new CountDownLatch(size);
        for (int i=0;i<size;i++){
            pool1.execute(()->{
                for (int j =0;j<10000000;j++){
//                    chunkSycn.increase1();
                }
                latch1.countDown();
            });
        }
        ExecutorService pool2 = Executors.newFixedThreadPool(size);
        CountDownLatch latch2 = new CountDownLatch(size);
        for (int i=0;i<size;i++){
            pool2.execute(()->{
                for (int j =0;j<10000000;j++){
                    chunkSycn.increase2(chunkSycn);
                }
                latch2.countDown();
            });
        }
        latch.await();
        latch1.await();
        latch2.await();
        System.out.println(chunkSycn.count);
        System.out.println(staticCount);
        pool.shutdown();
        pool1.shutdown();
        pool2.shutdown();
    }
}
