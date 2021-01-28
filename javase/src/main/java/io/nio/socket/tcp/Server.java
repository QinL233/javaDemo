package io.nio.socket.tcp;

import io.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:13
 */
public class Server {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel s1 = ServerSocketChannel.open();
        s1.socket().bind(new InetSocketAddress(8081));
        s1.configureBlocking(false);
        //server只能监听accept
        s1.register(selector, SelectionKey.OP_ACCEPT, "AaA");
        ServerSocketChannel s2 = ServerSocketChannel.open();
        s2.socket().bind(new InetSocketAddress(8082));
        s2.configureBlocking(false);
        s2.register(selector, SelectionKey.OP_ACCEPT, "BbB");

        ByteBuffer buffer = ByteBuffer.allocate(2 << 10);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    //第一次连接，获取到socket并注册到select中
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel client = channel.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ, key.attachment());
                } else if (key.isReadable()) {
                    //接收到client发送的消息
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.read(buffer);
                    buffer.flip();
                    CharBuffer msg = Charset.forName(Config.utf8).decode(buffer);
                    System.out.println(key.attachment() + " received:" + msg);
                    buffer.clear();
                    channel.write(Charset.forName(Config.utf8).encode(String.valueOf(Integer.valueOf(msg.toString()) + 1)));
                }
                iterator.remove();
            }
        }
    }
}
