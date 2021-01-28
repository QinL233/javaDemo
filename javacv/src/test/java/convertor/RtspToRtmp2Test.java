package convertor;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:05
 */
public class RtspToRtmp2Test {
    public void test() throws Exception {
        //String source = "rtmp://58.200.131.2:1935/livetv/cctv10";
        //String source = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
        String source = "rtsp://admin:hik12345@192.168.110.205:554/h264/ch1/main/av_stream";
        String outputFile = "test2.flv";
        //String outputFile = "rtmp://192.168.30.21/live/record1";
        //String outputFile = "rtmp://192.168.119.133:1935/live/home";
        //运行，设置视频源和推流地址
        new RtmpTpRtmp2().from(source).to(outputFile);
    }
}
