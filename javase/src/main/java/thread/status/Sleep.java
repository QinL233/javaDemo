package thread.status;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:34
 */
public class Sleep {
    /**
     * 在主线程中调用子线程时，主线程比子线程先执行完
     * sleep：谁调用谁阻塞,阻塞时并不会释放资源[锁]
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"主线程开始");
        new Thread(()->{
            for (int i=0;i<10;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("<");
            }
        },"demo1").start();
        new Thread(()->{
            for (int i=0;i<10;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(">");
            }
        },"demo2").start();
        System.out.println(Thread.currentThread().getName()+"主结束开始");
    }
}
