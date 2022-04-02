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
 * @createTime 2021��10��25�� 17:56:00
 */
public class VideoParser {

    /**
     * �ϲ���Ƶ��ʹ��H264���룬ָ��Ĭ����Ƶ����
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
            //��ȡ��Ƶ����
            int audioCodec = grabber.getAudioCodec();
            int videoCodec = grabber.getVideoCodec();
            double frameRate = grabber.getFrameRate();
            int videoBitrate = grabber.getVideoBitrate();
            //��ȡͼƬ����
            int width = grabber.getImageWidth();
            int height = grabber.getImageHeight();
            //��ȡ��Ƶ����
            int audioChannels = grabber.getAudioChannels();
            int audioBitrate = grabber.getAudioBitrate();
            if (audioBitrate < 1) {
                audioBitrate = 128 * 1000;// Ĭ����Ƶ������
            }
            int sampleRate = grabber.getSampleRate();

            //��װ��ʽflv
            recorder.setFormat("mp4");
//            recorder.setVideoOption("crf", "18");
//            recorder.setGopSize(2);
            //��Ƶ����
            recorder.setVideoCodec(videoCodec);
            recorder.setVideoCodec(AV_CODEC_ID_H264);
            recorder.setFrameRate(frameRate);
            recorder.setVideoBitrate(videoBitrate);
            recorder.setAudioCodec(audioCodec);
            //��Ƶ���������������У�audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
            recorder.setSampleRate(sampleRate);
            recorder.setAudioChannels(audioChannels);
            recorder.setAudioBitrate(audioBitrate);
            //��ʼ����
            recorder.start();

            //����֡
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
