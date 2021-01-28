package io.bio.chars;

import io.Config;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:04
 */
public class BufferFile {
    public static void main(String[] args) {
        try(Reader reader=new BufferedReader(new FileReader(new File(Config.file)));
            Writer writer=new BufferedWriter(new FileWriter(new File(Config.file)));
        ) {
            writer.write("sssss");
            writer.flush();
            char[] chars=new char[2 << 10];
            int len;
            while ((len =reader.read(chars)) != -1){
                System.out.println(chars);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
