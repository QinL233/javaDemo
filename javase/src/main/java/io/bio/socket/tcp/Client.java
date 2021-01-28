package io.bio.socket.tcp;

import io.Config;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:18
 */
public class Client {
    public void send() throws IOException {
        Socket socket = new Socket(Config.host, Config.port);
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("hello?");
        writer.close();
        socket.close();
    }
}
