package thread.create;

public class ThreadExt extends Thread{
    public static void main(String[] args) {
        new ThreadExt().start();
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName());
        }
    }
}