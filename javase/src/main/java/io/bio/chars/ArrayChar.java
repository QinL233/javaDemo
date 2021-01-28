package io.bio.chars;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:04
 */
public class ArrayChar {
    public static void main(String[] args) {
        String msg="test";
        try{
            Reader is=new CharArrayReader(msg.toCharArray());
            Writer os=new CharArrayWriter();
            char[] chars=new char[2<<9];
            int len;
            while ((len=is.read(chars)) != -1){
                os.write(chars);
                os.flush();
            }
            System.out.println(os.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
