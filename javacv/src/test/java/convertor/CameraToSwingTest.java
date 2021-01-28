package convertor;

import org.junit.Test;

import java.io.File;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:06
 */
public class CameraToSwingTest {
    @Test
    public void test() throws Exception {
        String project = System.getProperty("user.dir");
        CameraToSwing convertor = new CameraToSwing();
        String outputFile = project+File.separator +"test.flv";
        convertor.convertor(outputFile);
    }
}
