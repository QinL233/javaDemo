package io.bio.bytes;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:59
 */
public class PipeByte {
    public static void main(String[] args) {

        PipedInputStream pis=new PipedInputStream();
        PipedOutputStream pos=new PipedOutputStream();
        try {
            pis.connect(pos);
            new Thread(()->{
                try {
                    byte[] bytes=new byte[2 << 9];
                    int len;
                    while ((len=pis.read(bytes)) == -1){
                        Thread.sleep(1000);
                        System.out.println("wait...");
                    }
                    System.out.println("get:"+new String(bytes,0,len));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(3000);
            new Thread(()->{
                try {
                    String msg="test";
                    pos.write(msg.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
