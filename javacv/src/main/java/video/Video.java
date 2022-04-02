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
public class Video {

    public static void main(String[] args) {
        String path = "C:/Users/liaoqinzhou_sz/Downloads/为什么每个人都要懂点战略.mp4";
        try (
                InputStream is = new FileInputStream(path);
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(is);
        ) {
            grabber.start();
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
