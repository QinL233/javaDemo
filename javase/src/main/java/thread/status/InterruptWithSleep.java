package thread.status;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:29
 */
public class InterruptWithSleep {
    /**
     * 线程在wait/sleep /join状态时interrupt会抛出InterruptedException
     * @param args
     */
    public static void main(String[] args) {
        Thread demo1 = new Thread(() -> {
            for(int i=0;i<260;i++){
                Thread.yield();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //若线程在sleep状态被打断则中断
                    e.printStackTrace();
                    return;
                }
                System.out.print((char)(97+i%26));
            }
        },"demo1");
        Thread demo2 = new Thread(() -> {
            for (int i = 0; i < 260; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(i);
            }
        }, "demo2");
        demo1.start();
        demo2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        demo1.interrupt();
        demo2.interrupt();
    }
}
