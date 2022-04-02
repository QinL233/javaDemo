package video;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import java.io.*;

import static org.bytedeco.javacpp.avcodec.AV_CODEC_ID_H264;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年10月25日 17:56:00
 */
public class VideoParser {

    /**
     * 合并视频，使用H264编码，指定默认视频参数
     *
     * @param path
     * @param path2
     */
    private static void parser(String path, String path2) {
        String target = "D:/video/target.mp4";
        try (
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(path);
                FFmpegFrameGrabber grabber2 = new FFmpegFrameGrabber(path2);
                FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(target, 1280, 720);
        ) {
            grabber.start();
            grabber2.start();
            //获取视频参数
            int audioCodec = grabber.getAudioCodec();
            int videoCodec = grabber.getVideoCodec();
            double frameRate = grabber.getFrameRate();
            int videoBitrate = grabber.getVideoBitrate();
            //获取图片长宽
            int width = grabber.getImageWidth();
            int height = grabber.getImageHeight();
            //获取音频参数
            int audioChannels = grabber.getAudioChannels();
            int audioBitrate = grabber.getAudioBitrate();
            if (audioBitrate < 1) {
                audioBitrate = 128 * 1000;// 默认音频比特率
            }
            int sampleRate = grabber.getSampleRate();

            //封装格式flv
            recorder.setFormat("mp4");
//            recorder.setVideoOption("crf", "18");
//            recorder.setGopSize(2);
            //视频参数
            recorder.setVideoCodec(videoCodec);
            recorder.setVideoCodec(AV_CODEC_ID_H264);
            recorder.setFrameRate(frameRate);
            recorder.setVideoBitrate(videoBitrate);
            recorder.setAudioCodec(audioCodec);
            //音频这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
            recorder.setSampleRate(sampleRate);
            recorder.setAudioChannels(audioChannels);
            recorder.setAudioBitrate(audioBitrate);
            //开始接收
            recorder.start();

            //读入帧
            Frame frame = null;
            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
            }
            frame = null;
            while ((frame = grabber2.grab()) != null) {
                recorder.record(frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
