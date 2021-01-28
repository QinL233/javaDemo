package io.nio.channel;

import io.Config;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:11
 */
public class ChannelTransfer {
    public static void main(String[] args) {
        try (
                RandomAccessFile f1 = new RandomAccessFile(Config.file, "rw");
                RandomAccessFile f2 = new RandomAccessFile(Config.file2, "rw");) {

            FileChannel c1 = f1.getChannel();
            FileChannel c2 = f2.getChannel();
            //c2在末尾新增c1
            c2.transferFrom(c1,c2.size(),c1.size());
            //c1将0-n个数存入c2
            c2.position(c2.size());
            c1.transferTo(0,c1.size(),c2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
