package thread.create;


import java.util.concurrent.*;

public class CallableImpl {
    public static void main(String[] args) {
        int size = 5;
        ExecutorService pool = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            Future future = pool.submit(new MyCall());
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
    }

}


class MyCall implements Callable {

    @Override
    public Object call() throws Exception {
        return Thread.currentThread().getName();
    }
}