package io.bio.chars;

import io.Config;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:05
 */
public class PrintChar {
    public static void main(String[] args) {
        try (PrintWriter pw=new PrintWriter(new FileWriter(new File(Config.file)));
        ){
            pw.println("hehehe");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
