package io.bio.socket.tcp;

import io.Config;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:18
 */
public class Server {
    public void receiver() throws IOException {
        ServerSocket server = new ServerSocket(Config.port);
        System.out.println("server running");
        //阻塞进程，直到获取到数据
        Socket socket = server.accept();
        //获取is
        InputStream is = socket.getInputStream();
        byte[] bytes = new byte[2 << 10];
        int len;
        while ((len = is.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, len));
        }
        is.close();
        socket.close();
        server.close();
    }
}
