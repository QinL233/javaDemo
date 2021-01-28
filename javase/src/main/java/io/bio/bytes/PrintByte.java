package io.bio.bytes;

import io.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 10:00
 */
public class PrintByte {
    public static void main(String[] args) {
        PrintStream out = System.out;
        out.println("hello world");
        try (PrintStream ps=new PrintStream(new FileOutputStream(new File(Config.resources,Config.fileName)));
        ){
            ps.println("hehe");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
