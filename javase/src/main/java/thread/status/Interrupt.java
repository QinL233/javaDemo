package thread.status;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:29
 */
public class Interrupt {
    /**
     * 中断线程：给该线程的发送中断信号，并非直接关闭
     * @param args
     */
    public static void main(String[] args) {
        Thread demo1 = new Thread(() -> {
            for(int i=0;i<260;i++){
                Thread.yield();
                System.out.print((char)(97+i%26));
            }
        },"demo1");
        Thread demo2 = new Thread(() -> {
            for (int i = 0; i < 260; i++) {
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
