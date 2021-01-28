package io.bio.bytes;

import io.Config;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:51
 */
public class BufferFile {
    public static void main(String[] args) {

        try(
                OutputStream os= new BufferedOutputStream(new FileOutputStream(new File(Config.resources,Config.fileName)));
                InputStream is= new BufferedInputStream(new FileInputStream(new File(Config.resources,Config.fileName)));
        ){
            String msg="why?";
            os.write(msg.getBytes());
            os.flush();
            byte[] rs=new byte[2<<10];
            int len=-1;
            while ((len=is.read(rs)) != -1){
                System.out.println(new String(rs,0,len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
