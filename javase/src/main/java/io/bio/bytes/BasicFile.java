package io.bio.bytes;

import io.Config;

import java.io.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:21
 */
public class BasicFile {
    public static void main(String[] args) throws IOException {
        try(
                OutputStream os= new FileOutputStream(new File(Config.resources,Config.fileName),true);
                InputStream is= new FileInputStream(new File(Config.resources,Config.fileName));
        ){
            String msg="what?";
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
