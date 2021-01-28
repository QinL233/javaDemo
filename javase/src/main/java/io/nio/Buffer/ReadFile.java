package io.nio.Buffer;

import io.Config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:09
 */
public class ReadFile {
    public static void main(String[] args) {
        try {
            //获取channel
            RandomAccessFile file = new RandomAccessFile(Config.file, "rw");
            FileChannel channel = file.getChannel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(2 << 1);
            //通过管道写入buffer中
            int len = -1;
            while ((len = channel.read(buffer)) != -1) {
                //切换读取模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                //清空所有
                buffer.clear();
                //清空已读
                //buffer.compact();
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
