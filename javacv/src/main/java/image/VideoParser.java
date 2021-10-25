package image;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021��10��25�� 17:56:00
 */
public class VideoParser {

    public static void main(String[] args) {
        String p1 = "D:/video/1.mp4";
        String p2 = "D:/video/2.wmv";
        parser(p1, p2);
    }

    /**
     * ������Ƶ�ϲ�
     *
     * @param path
     * @param path2
     */
    private static void parser(String path, String path2) {
        String target = "D:/video/target.mp4";
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(path);
            grabber.start();
            FFmpegFrameGrabber grabber2 = new FFmpegFrameGrabber(path2);
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
            String audioCodecName = grabber.getAudioCodecName();

            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(target, width, height, audioChannels);
            //��װ��ʽflv
            recorder.setFormat("mp4");
//            recorder.setVideoOption("crf", "18");
//            recorder.setGopSize(2);
            //��Ƶ����
            recorder.setVideoCodec(videoCodec);
//            record.setVideoCodec(AV_CODEC_ID_H264);
            recorder.setFrameRate(frameRate);
            recorder.setVideoBitrate(videoBitrate);
            recorder.setAudioCodec(audioCodec);
            recorder.setAudioCodecName(audioCodecName);
            //��Ƶ���������������У�audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
            recorder.setSampleRate(sampleRate);
            recorder.setAudioChannels(audioChannels);
            recorder.setAudioBitrate(audioBitrate);
//            avformat.AVFormatContext fc = grabber.getFormatContext();
            //��ʼ����
            recorder.start();
            Frame frame = null;
            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
            }
            frame = null;
            while ((frame = grabber2.grab()) != null) {
                recorder.record(frame);
            }
            recorder.close();
            grabber.close();
            grabber2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
