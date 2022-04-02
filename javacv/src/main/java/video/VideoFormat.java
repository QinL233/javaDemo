package video;

import org.bytedeco.javacv.FFmpegFrameGrabber;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年12月20日 09:56:00
 */
public class VideoFormat {

    public static void main(String[] args) {
        String path = "C:/Users/liaoqinzhou_sz/Downloads/1.mp4";
        String path2 = "C:/Users/liaoqinzhou_sz/Videos/大数据应用实战/01-开发环境及工具介绍.mp4";
        try (
                InputStream is = new FileInputStream(path);
                InputStream is2 = new FileInputStream(path2);
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(is);
                FFmpegFrameGrabber grabber2 = new FFmpegFrameGrabber(is2);
        ) {
            grabber.start();
            grabber2.start();
            int audioCodec = grabber.getAudioCodec();
            int videoCodec = grabber.getVideoCodec();
            double frameRate = grabber.getFrameRate();
            int videoBitrate = grabber.getVideoBitrate();
            int width = grabber.getImageWidth();
            int height = grabber.getImageHeight();
            int audioChannels = grabber.getAudioChannels();
            int audioBitrate = grabber.getAudioBitrate();
            int sampleRate = grabber.getSampleRate();
            System.out.println(grabber.getFormat());
            System.out.println("finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
