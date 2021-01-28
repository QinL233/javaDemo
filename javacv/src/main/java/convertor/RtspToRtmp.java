package convertor;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder;

import static org.bytedeco.javacpp.avcodec.av_packet_unref;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/7 15:58
 */
public class RtspToRtmp {

    /**
     * 将视频rtsp视频推送到rtmp
     *
     * @param source
     * @param target
     */
    public void convert(String source, String target) {
        FFmpegFrameGrabber grabber = null;
        FFmpegFrameRecorder recorder = null;
        try {
            //1.采集/抓取器输入
            grabber = new FFmpegFrameGrabber(source);
            if (source.indexOf("rtsp") >= 0) {
                grabber.setOption("rtsp_transport", "tcp");
            }
            //开始之后ffmpeg会采集视频信息，之后就可以获取音视频信息
            grabber.start();
            int width = grabber.getImageWidth();
            int height = grabber.getImageHeight();
            //视频参数
            int codecid = grabber.getVideoCodec();
            // 帧率
            double framerate = grabber.getVideoFrameRate();
            // 比特率
            int bitrate = grabber.getVideoBitrate();
            int audioCodec = grabber.getAudioCodec();
            String audioCodecName = grabber.getAudioCodecName();
            //音频参数
            int sampleRate = grabber.getSampleRate();
            int audioChannels = grabber.getAudioChannels();
            int audioBitrate = grabber.getAudioBitrate();
            if (audioBitrate < 1) {
                audioBitrate = 128 * 1000;// 默认音频比特率
            }
            System.out.println("grabber success");
            //2.录制/推流器输出
            recorder = new FFmpegFrameRecorder(target, width, height);
            //封装格式flv
            recorder.setFormat("flv");
            recorder.setVideoOption("crf", "18");
            recorder.setGopSize(2);
            //视频参数
            recorder.setVideoCodec(codecid);
//            record.setVideoCodec(AV_CODEC_ID_H264);
            recorder.setFrameRate(framerate);
            recorder.setVideoBitrate(bitrate);
            recorder.setAudioCodec(audioCodec);
            recorder.setAudioCodecName(audioCodecName);
            //想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
            recorder.setSampleRate(sampleRate);
            recorder.setAudioChannels(audioChannels);
            recorder.setAudioBitrate(audioBitrate);
            avformat.AVFormatContext fc = grabber.getFormatContext();
            //开始接收
            recorder.start(fc);
            System.out.println("recorder success");

            //3.开始转换
            //采集或推流导致的错误次数
            long err_index = 0;
            //连续五次没有采集到帧则认为视频采集结束，程序错误次数超过1次即中断程序
            for (int no_frame_index = 0; no_frame_index < 5 && err_index < 1; ) {
                avcodec.AVPacket pkt = null;
                try {
                    //没有解码的音视频帧
                    pkt = grabber.grabPacket();
                    if (pkt == null || pkt.size() <= 0 || pkt.data() == null) {
                        //空包记录次数跳过
                        no_frame_index++;
                        continue;
                    }
                    //如果失败err_index自增
                    if(!recorder.recordPacket(pkt)){
                        err_index++;
                    }
                    //不需要编码直接把音视频帧推出去
                    av_packet_unref(pkt);
                } catch (Exception e) {//推流失败
                    err_index++;
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (grabber != null) {
                try {
                    grabber.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (recorder != null) {
                try {
                    recorder.stop();
                } catch (FrameRecorder.Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
