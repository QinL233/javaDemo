package io.bio.bytes;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:58
 */
public class ArrayByte {
    public static void main(String[] args) {
        String msg="test";
        try{
            InputStream is=new ByteArrayInputStream(msg.getBytes());
            OutputStream os=new ByteArrayOutputStream();
            byte[] bytes=new byte[2<<9];
            int len;
            while ((len=is.read(bytes)) != -1){
                os.write(bytes);
                os.flush();
            }
            System.out.println(os.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
