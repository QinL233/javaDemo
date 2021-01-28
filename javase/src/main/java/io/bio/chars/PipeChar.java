package io.bio.chars;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:05
 */
public class PipeChar {
    public static void main(String[] args) {
        PipedReader reader=new PipedReader();
        PipedWriter writer=new PipedWriter();
        try {
            reader.connect(writer);
            new Thread(()->{
                try {
                    Thread.sleep(1000);
                    char[] chars=new char[2 << 9];
                    int len=reader.read(chars);
                    System.out.println(chars);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(2000);
            new Thread(()->{
                try {
                    String msg="test";
                    writer.write(msg.toCharArray());
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
