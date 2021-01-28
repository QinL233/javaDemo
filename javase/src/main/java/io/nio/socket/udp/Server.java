package io.nio.socket.udp;

import io.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:14
 */
public class Server {
    public static void main(String[] args) throws IOException {
        //初始化selector,可以监听多个channel，实现单线程管理多个通道
        Selector selector = Selector.open();

        //channel
        //打开udp
        DatagramChannel c1 = DatagramChannel.open();
        c1.socket().bind(new InetSocketAddress(8081));
        //非阻塞模式才可被selector监听
        c1.configureBlocking(false);
        //selector注册channel可以附加/携带信息
        c1.register(selector, SelectionKey.OP_READ, "a");
        DatagramChannel c2 = DatagramChannel.open();
        c2.socket().bind(new InetSocketAddress(8082));
        c2.configureBlocking(false);
        c2.register(selector, SelectionKey.OP_READ, "b");

        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(2 << 4);

        //accept
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    System.out.println("accept:" + key.attachment());
                } else if (key.isConnectable()) {
                    System.out.println("connect:" + key.attachment());
                } else if (key.isReadable()) {
                    System.out.println("read:" + key.attachment());
                    DatagramChannel channel = (DatagramChannel) key.channel();
                    //buffer写入
                    channel.receive(buffer);
                    buffer.flip();
                    System.out.println(Charset.forName(Config.utf8).newDecoder().decode(buffer).toString());
                } else if (key.isWritable()) {
                    System.out.println("write:" + key.attachment());
                }
                iterator.remove();
            }
        }
    }
}
