package io.nio.Buffer;

import io.Config;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:10
 */
public class GatherBuffer {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile(Config.file, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer header = ByteBuffer.allocate(2);
        ByteBuffer body = ByteBuffer.allocate(4);
        ByteBuffer footer = ByteBuffer.allocate(2);
        ByteBuffer[] buffers = new ByteBuffer[]{header, body, footer};
        //channel将数据写入buffer,buffer为写状态
        System.out.println(channel.read(buffers));
        for (ByteBuffer buffer : buffers) {
            buffer.flip();
        }
        //重新将buffers的值输出到chanel,buffers为读取
        System.out.println(channel.write(buffers));
        file.close();
    }
}
