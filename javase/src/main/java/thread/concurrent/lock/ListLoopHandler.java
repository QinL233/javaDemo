package thread.concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月02日 10:24:00
 */
public class ListLoopHandler {

    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 10L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(2000000), new ThreadPoolExecutor.DiscardPolicy());

    public ListLoopHandler(String name, Integer size, LoopingHandler handler) {
        CountDownLatch countDownLatch = new CountDownLatch(size);
        List<String> list = new ArrayList<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            pool.execute(() -> {
                try {
                    handler.handler(list);
                } catch (Exception e) {
                    System.out.println("loop list err:" + e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("%s %d size on %d ms", name, list.size(), System.currentTimeMillis() - begin));
    }
}
