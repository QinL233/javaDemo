package io.bio.bytes;

import io.Config;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:55
 */
public class DataFile {
    public static void main(String[] args) {
        try(
                DataInputStream dis=new DataInputStream(new FileInputStream(new File(Config.resources,Config.fileName)));
                DataOutputStream ois=new DataOutputStream(new FileOutputStream(new File(Config.resources,Config.fileName)));
        ){
            String msg1="test";
            Integer msg2=9465416;
            Boolean msg3=true;
            ois.writeUTF(msg1);
            ois.writeInt(msg2);
            ois.writeBoolean(msg3);
            ois.flush();
            System.out.println(dis.readUTF());
            System.out.println(dis.readInt());
            System.out.println(dis.readBoolean());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
