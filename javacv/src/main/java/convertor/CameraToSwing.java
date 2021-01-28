package convertor;

import org.bytedeco.javacv.*;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.bytedeco.javacpp.avcodec.AV_CODEC_ID_AAC;
import static org.bytedeco.javacpp.avcodec.AV_CODEC_ID_H264;


/**
 * 按帧录制，摄像头数据
 * 既录制功能与推流功能(将流存储本地则为录制，将流放上rtmp协议则为推流)
 *
 * @author LiaoQinZhou
 * @date: 2020/9/16 18:10
 * @description:
 */
public class CameraToSwing {

    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public void convertor(String outputFile) throws Exception {
        //1.定义解码器,获取本机摄像头
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        //开启抓取
        grabber.start();

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

        //2.定义接收器
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, width, height);
        //转码参数
        recorder.setVideoOption("crf", "18");
        //清晰度
        recorder.setGopSize(2);
        //帧数
        recorder.setFrameRate(25);
        //比特率
        recorder.setVideoBitrate(4000);
        //设置视频文件编码格式
        recorder.setVideoCodec(AV_CODEC_ID_H264);
        recorder.setAudioCodec(AV_CODEC_ID_AAC);
        //设置视频文件封装格式
        recorder.setFormat("flv");
        //音频参数
        recorder.setAudioChannels(audioChannels);
        recorder.setAudioBitrate(audioBitrate);
        recorder.setSampleRate(sampleRate);
        //开始录制
        recorder.start();

        //3.定义swing窗口
        CanvasFrame canvas = new CanvasFrame("camera", CanvasFrame.getDefaultGamma());
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);

        //4.定义转换器
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();

        //启动线程处理帧
        pool.execute(() -> {
            try {
                Frame frame = null;
                //循环，窗口不关闭就继续抓取
                while (canvas.isVisible()
                        && (frame = grabber.grab()) != null) {
                    //将帧输入到record
                    recorder.record(frame);
                    //将帧输入到swing
                    canvas.showImage(frame);
                    //缓解阻塞
                    Thread.sleep(30);
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    //关闭资源
                    canvas.dispose();
                    recorder.close();
                    grabber.close();
                } catch (FrameRecorder.Exception e) {
                    e.printStackTrace();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
