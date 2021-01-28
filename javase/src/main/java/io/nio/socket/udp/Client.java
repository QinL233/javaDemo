package io.nio.socket.udp;

import io.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:14
 */
public class Client {
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.next();
            //将msg转换成buffer，此时是读取状态
            ByteBuffer buffer = Charset.forName(Config.utf8).encode(msg);
            try {
                channel.send(buffer, new InetSocketAddress("127.0.0.1", 8081));
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.clear();
        }
        channel.close();
    }
}
