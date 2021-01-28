package thread.status;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:39
 */
public class Wait {
    public static void main(String[] args) throws InterruptedException {
        //使用0长度的byte[]数组当锁最经济
        byte[] t1=new byte[0];
        byte[] t2=new byte[0];
        byte[] t3=new byte[0];
        new Thread(new Print("1",t1,t2)).start();
        Thread.sleep(100);
        new Thread(new Print("2",t2,t3)).start();
        Thread.sleep(100);
        new Thread(new Print("3",t3,t1)).start();
    }
}


/**
 * 循序线程：a-->b-->c-->a
 * 1.首先每个线程有自己的锁，锁自己，由前人释放
 * 2.其次每个线程能有一把后人的锁，锁后人，（自己执行完）则释放这把锁让后人执行
 * 说明：形成一条锁链，自己被前一个线程锁，自己锁住下一个线程；当第一个线程释放后，第二个线程被释放，解说下一个线程；链式结构可以形成环
 */
class Print implements Runnable{
    private String flag;
    private byte[] self;
    private byte[] following;
    public Print(String flag,byte[] self,byte[] following){
        this.flag=flag;
        this.self=self;
        this.following=following;
    }

    @Override
    /**
     * 1.wait():使当前线程阻塞（执行完会直接释放锁）【进入wait池】，前提是必须先获取到对象锁，即在synchronized中执行否则抛出“IllegalMonitorStateException”
     * 2.notify():唤醒【wait池】中阻塞的对象，（执行后并不会释放锁），若在synchronized语句中，则执行完syn语句后会才释放锁
     * 注意：notify只能唤醒wait中的对象，注意执行顺序
     */
    public void run() {
        while (true) {
            synchronized (self) {
                synchronized (following) {
                    if("3".equals(flag)){
                        System.out.println(flag);
                    }else{
                        System.out.print(flag);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //唤醒下一个
                    following.notify();
                }
                try {
                    //阻塞自己
                    self.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}