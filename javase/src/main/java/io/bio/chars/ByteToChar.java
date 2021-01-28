package io.bio.chars;

import io.Config;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:06
 */
public class ByteToChar {
    public static void main(String[] args) {
        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(new File(Config.file))));
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Config.file),false)));
            writer.write("transfer");
            writer.flush();
            char[] chars=new char[2 << 10];
            int len;
            while ((len =reader.read(chars)) != -1){
                System.out.println(chars);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
