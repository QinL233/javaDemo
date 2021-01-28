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

    public static void main(String[] args) throws FileNotFoundException {
        String path = "D:\\JetBrains\\project\\JavaSE\\Gof23\\src\\main\\java";
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
            try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
                 ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
                FileChannel channel = raf.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(1);
                int len = -1;
                while ((len = channel.read(buffer)) > 0) {
                    //读取
                    buffer.flip();
                    bos.write(buffer.get());
                    buffer.flip();
                }
                CharsetDetector detector = new CharsetDetector();
                detector.setText(bos.toByteArray());
                CharsetMatch match = detector.detect();
                //获取源编码
                String charsetName = match.getName();
                switch (charsetName) {
                    case "EUC-KR":
                        charsetName = "GB2312";
                        break;
                    case "ISO-8859-1":
                        charsetName = "GBK";
                        break;
                }
                if (!"UTF-8".equals(charsetName)) {
                    System.out.println("The Content in " + charsetName);
                    //按照charsetName解码成String
                    String s = new String(bos.toByteArray(), charsetName);
//                    System.out.println(s);
                    //将String按utf-8加密成buffer
                    ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(s);
                    //覆盖源文件
                    channel.position(0);
                    channel.write(byteBuffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
