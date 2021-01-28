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
public class ScatterBuffer {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile(Config.file, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer header = ByteBuffer.allocate(2);
        ByteBuffer body = ByteBuffer.allocate(4);
        ByteBuffer footer = ByteBuffer.allocate(2);
        ByteBuffer[] buffers = new ByteBuffer[]{header, body, footer};
        //channel将数据写入buffer,buffer为写状态
        channel.read(buffers);
        for (ByteBuffer buffer : buffers) {
            //buffer切换读状态,position指到数据初始位
            buffer.flip();
            //一个个输出
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            System.out.println();
            //清理
            buffer.clear();
        }
        file.close();
    }
}
