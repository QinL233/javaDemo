package thread.status;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:44
 */
public class Priority {
    /**
     * setPriority(): 更改线程的优先级。
     * @param args
     */
    public static void main(String[] args) {
        Thread demo1 = new Thread(() -> {
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+(char)(97+i));
            }
        },"demo1");
        Thread demo2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+i);
            }
        }, "demo2");
        demo1.setPriority(Thread.MIN_PRIORITY);
        demo2.setPriority(Thread.MAX_PRIORITY);
        demo1.start();
        demo2.start();
    }
}
