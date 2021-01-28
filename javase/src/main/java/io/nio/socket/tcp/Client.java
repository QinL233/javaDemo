package io.nio.socket.tcp;

import io.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:13
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 8081));
        channel.register(selector, SelectionKey.OP_CONNECT);
        ByteBuffer buffer = ByteBuffer.allocate(2 << 10);
        //直到连接成功
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isConnectable()) {
                    System.out.println("connected:" + channel.finishConnect());
                    //连接成功,监听读事件,并发送一条消息
                    SocketChannel c = (SocketChannel) key.channel();
                    c.configureBlocking(false);
                    c.register(selector, SelectionKey.OP_READ);
                    channel.write(Charset.forName(Config.utf8).encode("1"));
                } else if (key.isReadable()) {
                    SocketChannel c = (SocketChannel) key.channel();
                    c.read(buffer);
                    buffer.flip();
                    CharBuffer msg = Charset.forName(Config.utf8).decode(buffer);
                    System.out.println("client received:" + msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channel.write(Charset.forName(Config.utf8).encode(String.valueOf(Integer.valueOf(msg.toString()) + 1)));
                }
            }
        }
    }
}
