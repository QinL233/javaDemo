package image;


import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年08月05日 16:02:00
 */
public class ReadMat {
    public static void main(String[] args) {
        Mat mat = imread("C:/Users/liaoqinzhou_sz/Pictures/test/20210624143627.png");
        if (mat == null) {
            System.out.println("读取图像失败,mat为空mat=" + mat);
            return;
        }
        //读取第一行,第一列,第一个通道的元素
        int row = 0;
        int col = 0;
        int channel = 0;
        System.out.println(getMatElement(mat, row, col, channel));
        //使用旧版本CvMat进行验证结果是否正确
//        CvMat cvMat = mat.asCvMat();
//        System.out.println(cvMat.get(0, 0, channel));

    }

    /**
     * @param img     mat类型的图像
     * @param row     行
     * @param col     列
     * @param channel 通道
     * @return int
     * 像素值
     * @功能说明:读取mat元素的值
     * @time:2015年9月4日下午2:52:23
     * @author:linghushaoxia
     * @exception:
     */
    public static int getMatElement(Mat img, int row, int col, int channel) {
        //获取字节指针
        BytePointer bytePointer = img.ptr(row, col);
        int value = bytePointer.get(channel);
        if (value < 0) {
            value = value + 256;
        }
        return value;
    }
}
