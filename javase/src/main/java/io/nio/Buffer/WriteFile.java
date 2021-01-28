package io.nio.Buffer;

import io.Config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:08
 */
public class WriteFile {
    public static void main(String[] args) {
        String msg = "write";
        try {
            RandomAccessFile file = new RandomAccessFile(Config.file, "rw");
            FileChannel channel = file.getChannel();
            //强制写入
            channel.force(true);
            ByteBuffer buffer = Charset.forName("utf8").encode(msg);
            channel.write(buffer);
            //截取前n个
            channel.truncate(4);
            //关闭file,注:fileChannel必须关闭,file中close已关闭
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
