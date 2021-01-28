package convertor;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.bytedeco.javacpp.avcodec.*;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 08:57
 */
public class RtmpTpRtmp2 {

    private static final ExecutorService pool = Executors.newFixedThreadPool(10);
    //抓取器
    FFmpegFrameGrabber grabber = null;
    //接收器
    FFmpegFrameRecorder record = null;

    // 视频参数
    int width = -1, height = -1;
    protected int audiocodecid;
    protected int codecid;
    protected double framerate;// 帧率
    protected int bitrate;// 比特率

    // 音频参数
    // 想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
    private int audioChannels;
    private int audioBitrate;
    private int sampleRate;

    /**
     * 选择视频源
     *
     * @param src
     * @throws Exception
     * @author eguid
     */
    public RtmpTpRtmp2 from(String src) throws Exception {
        // 采集/抓取器
        grabber = new FFmpegFrameGrabber(src);
        if (src.indexOf("rtsp") >= 0) {
            grabber.setOption("rtsp_transport", "tcp");
        }
        grabber.start();// 开始之后ffmpeg会采集视频信息，之后就可以获取音视频信息
        if (width < 0 || height < 0) {
            width = grabber.getImageWidth();
            height = grabber.getImageHeight();
        }
        // 视频参数
        audiocodecid = grabber.getAudioCodec();
        codecid = grabber.getVideoCodec();
        framerate = grabber.getVideoFrameRate();// 帧率
        bitrate = grabber.getVideoBitrate();// 比特率
        // 音频参数
        audioChannels = grabber.getAudioChannels();
        audioBitrate = grabber.getAudioBitrate();
        if (audioBitrate < 1) {
            audioBitrate = 128 * 1000;// 默认音频比特率
        }
        sampleRate = grabber.getSampleRate();
        return this;
    }

    /**
     * 选择输出
     *
     * @param out
     * @throws IOException
     */
    public RtmpTpRtmp2 to(String out) throws IOException {
        // 录制/推流器
        record = new FFmpegFrameRecorder(out, width, height);
        // 转码参数
        record.setVideoOption("crf", "18");
        // 清晰度
        record.setGopSize(2);
        // 帧数
        record.setFrameRate(framerate);
        //音频参数
        record.setVideoBitrate(bitrate);
        record.setAudioChannels(audioChannels);
        record.setAudioBitrate(audioBitrate);
        record.setSampleRate(sampleRate);
        //定义ffmpeg结构体
        /**
         * eg:
         * AVIOContext *pb：输入数据的缓存
         * unsigned int nb_streams：视音频流的个数
         * AVStream **streams：视音频流
         * char filename[1024]：文件名
         * int64_t duration：时长（单位：微秒us，转换为秒需要除以1000000）
         * int bit_rate：比特率（单位bps，转换为kbps需要除以1000）
         * AVDictionary *metadata：元数据
         */
        //avformat.AVFormatContext fc = null;
        if (out.indexOf("rtmp") >= 0 || out.indexOf("flv") > 0) {
            //设置视频文件编码格式
            record.setVideoCodec(AV_CODEC_ID_H264);
            record.setAudioCodec(AV_CODEC_ID_AAC);
            // 封装格式flv
            record.setFormat("flv");
            //fc = grabber.getFormatContext();
        }
        //record.start(fc);
        record.start();

        //定义swing窗口
        CanvasFrame canvas = new CanvasFrame("camera", CanvasFrame.getDefaultGamma());
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);

        //启动线程处理数据帧
        pool.execute(() -> {
            try {
                Frame frame = null;
                //循环，窗口不关闭就继续抓取
                while (canvas.isVisible()
                        && (frame = grabber.grab()) != null) {
                    //接收该帧输入到out中
                    record.record(frame);
                    //窗口展示该frame
                    canvas.showImage(frame);
                    //缓解循环压力
                    Thread.sleep(30);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            } finally {
                //关闭资源
                try {
                    canvas.dispose();
                    record.close();
                    grabber.close();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                } catch (FrameRecorder.Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return this;
    }

    /**
     * 转封装
     *
     * @throws IOException
     */
    public RtmpTpRtmp2 go() throws IOException {
        long err_index = 0;//采集或推流导致的错误次数
        //连续五次没有采集到帧则认为视频采集结束，程序错误次数超过1次即中断程序
        for (int no_frame_index = 0; no_frame_index < 5 || err_index > 1; ) {
            avcodec.AVPacket pkt = null;
            try {
                //没有解码的音视频帧
                pkt = grabber.grabPacket();
                if (pkt == null || pkt.size() <= 0 || pkt.data() == null) {
                    //空包记录次数跳过
                    no_frame_index++;
                    continue;
                }
                //不需要编码直接把音视频帧推出去
                err_index += (record.recordPacket(pkt) ? 0 : 1);//如果失败err_index自增1
                System.out.println(err_index);
                av_free_packet(pkt);
            } catch (IOException e) {//推流失败
                System.out.println("++++++++++++++++++++++++++++++++++++++++++");
                err_index++;
            } catch (Exception e) {
                System.out.println("-------------------------------------------");
                err_index++;
            }
        }
        return this;
    }
}
