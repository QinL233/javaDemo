package io.file;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/28 15:27
 */
public class CharsetConvertTest {

    /**
     * 将目录下所有文件转换成默认编码
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("default charset is "+Charset.defaultCharset().name());
        String path = "D:\\test\\test";
        List<File> files = getFiles(path, new ArrayList<>());
        convert(files);
    }

    /**
     * 获取目录下所有文件文件
     *
     * @return
     */
    private static List<File> getFiles(String path, List<File> files) throws FileNotFoundException {
        File dir = new File(path);
        File[] child = dir.listFiles();
        for (File file : child) {
            //是否为文件夹
            if (file.isDirectory()) {
                getFiles(file.getAbsolutePath(), files);
            } else {
                files.add(file);
            }
        }
        return files;
    }

    /**
     * 将所有文件转换为utf8编码
     *
     * @param files
     */
    private static void convert(List<File> files) {
        files.forEach(file -> {
            try (RandomAccessFile raf = new RandomAccessFile(file, "rw");) {
                FileChannel channel = raf.getChannel();
                //一次读取
                ByteBuffer buffer = ByteBuffer.allocate(new Long(file.length()).intValue());
                channel.read(buffer);
                buffer.flip();
                CharsetDetector detector = new CharsetDetector();
                detector.setText(buffer.array());
                CharsetMatch match = detector.detect();
                //获取源编码
                String charsetName = match.getName();
                System.out.println(file.getName() +" content in " + charsetName);
                switch (charsetName) {
                    case "EUC-KR":
                        System.out.println(file.getName() +" charsetName from EUC-KR to GB2312");
                        charsetName = "GB2312";
                        break;
                    case "ISO-8859-1":
                        System.out.println(file.getName() +" charsetName from ISO-8859-1 to GBK");
                        charsetName = "GBK";
                        break;
                }
                if (!Charset.defaultCharset().name().equals(charsetName)) {
                    //按照charsetName解码成String
                    String s = new String(buffer.array(), charsetName);
                    System.out.println(s);
                    //将String按utf-8加密成buffer覆盖源文件
                    ByteBuffer byteBuffer = ByteBuffer.wrap(s.getBytes());
                    channel.position(0);
                    channel.write(byteBuffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
