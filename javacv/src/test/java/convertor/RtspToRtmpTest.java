package convertor;

import org.junit.Test;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/27 09:02
 */
public class RtspToRtmpTest {
    @Test
    public void convert(){
        String source = "rtsp://admin:hik12345@192.168.110.205:554/h264/ch1/sub/av_stream";
//        String source = "rtmp://58.200.131.2:1935/livetv/hunantv";
        String target = "rtmp://192.168.157.129:1935/live/test";
        new RtspToRtmp().convert(source, target);
    }
}
