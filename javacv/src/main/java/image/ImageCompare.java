package image;

import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年08月05日 15:16:00
 */
public class ImageCompare {
    public static void main(String[] args) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME);
        String imagePath1 = "C:/Users/liaoqinzhou_sz/Pictures/test/1.png";
        String imagePath2 = "C:/Users/liaoqinzhou_sz/Pictures/test/2.png";
        ImageCompare imageCompare = new ImageCompare();
        imageCompare.CompareAndMarkDiff(imagePath1, imagePath2);
    }

    private boolean compareResult = false;
    private String mark = "_compareResult";

    /**
     * 比较两张图片，如不同则将不同处标记并输出到新的图片中
     * @param imagePath1 图片1的路径
     * @param imagePath2 图片2的路径
     */
    public void CompareAndMarkDiff(String imagePath1, String imagePath2)
    {
        Mat mat1 = readMat(imagePath1);
        Mat mat2 = readMat(imagePath2);
        mat1 = Imgcodecs.imdecode(mat1, Imgcodecs.IMREAD_UNCHANGED);
        mat2 = Imgcodecs.imdecode(mat2, Imgcodecs.IMREAD_UNCHANGED);
//        Mat mat1 = Imgcodecs.imread(imagePath1, Imgcodecs.IMREAD_UNCHANGED);
//        Mat mat2 = Imgcodecs.imread(imagePath2, Imgcodecs.IMREAD_UNCHANGED);
        if(mat1.cols() == 0 || mat2.cols() == 0 || mat1.rows() == 0 || mat2.rows() == 0)
        {
            System.out.println("图片文件路径异常，获取的图片大小为0，无法读取");
            return;
        }
        if(mat1.cols() != mat2.cols() || mat1.rows() != mat2.rows())
        {
            System.out.println("两张图片大小不同，无法比较");
            return;
        }
        mat1.convertTo(mat1, CvType.CV_8UC1);
        mat2.convertTo(mat2, CvType.CV_8UC1);
        Mat mat1_gray = new Mat();
        Imgproc.cvtColor(mat1, mat1_gray, Imgproc.COLOR_BGR2GRAY);
        Mat mat2_gray = new Mat();
        Imgproc.cvtColor(mat2, mat2_gray, Imgproc.COLOR_BGR2GRAY);
        mat1_gray.convertTo(mat1_gray, CvType.CV_32F);
        mat2_gray.convertTo(mat2_gray, CvType.CV_32F);
        double result = Imgproc.compareHist(mat1_gray, mat2_gray, Imgproc.CV_COMP_CORREL);
        if(result == 1)
        {
            compareResult = true;//此处结果为1则为完全相同
            return;
        }
        System.out.println("similarity:"+result);
        Mat mat_result = new Mat();
        //计算两个灰度图的绝对差值，并输出到一个Mat对象中
        Core.absdiff(mat1_gray, mat2_gray, mat_result);
        //将灰度图按照阈值进行绝对值化
        mat_result.convertTo(mat_result, CvType.CV_8UC1);
        List<MatOfPoint> mat2_list = new ArrayList<MatOfPoint>();
        Mat mat2_hi = new Mat();
        //寻找轮廓图
        Imgproc.findContours(mat_result, mat2_list, mat2_hi, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat mat_result1 = mat1;
        Mat mat_result2 = mat2;
        //使用红色标记不同点
        System.out.println("differently:"+mat2_list.size());
        Scalar red = new Scalar(0, 0, 255,255);
        for (MatOfPoint matOfPoint : mat2_list)
        {
            Rect rect = Imgproc.boundingRect(matOfPoint);
            Imgproc.rectangle(mat_result1, rect.tl(), rect.br(),red ,10);
            Imgproc.rectangle(mat_result2, rect.tl(), rect.br(), red,10);
        }
        String fileName1 = getFileName(imagePath1);
        String targetPath1 = getParentDir(imagePath2)+File.separator+fileName1.replace(".", mark+".");
        String fileName2 = getFileName(imagePath2);
        String targetPath2 = getParentDir(imagePath2)+File.separator+fileName2.replace(".", mark+".");
        System.out.println(targetPath1);
        System.out.println(targetPath2);
        //图片一的带标记的输出文件；
//        Imgcodecs.imwrite(targetPath1, mat_result1);
        //图片二的带标记的输出文件；
//        Imgcodecs.imwrite(targetPath2, mat_result2);
        writeImage(mat_result1, targetPath1);
        writeImage(mat_result2, targetPath2);
    }

    private void writeImage(Mat mat, String outPutFile)
    {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            ImageIO.write(bufImage, "png", new File(outPutFile));
        } catch (IOException | HeadlessException e)
        {
            e.printStackTrace();
        }
    }

    private String getFileName(String filePath)
    {
        File f = new File(filePath);
        return f.getName();
    }

    private String getParentDir(String filePath)
    {
        File f = new File(filePath);
        return f.getParent();
    }

    private Mat readMat(String filePath)
    {
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(filePath);
            byte[] byt = new byte[(int) file.length()];
            int read = inputStream.read(byt);
            List<Byte> bs = convert(byt);
            Mat mat1 = Converters.vector_char_to_Mat(bs);
            return mat1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Mat();
    }

    private List<Byte> convert(byte[] byt)
    {
        List<Byte> bs = new ArrayList<Byte>();
        for (int i = 0; i < byt.length; i++)
        {
            bs.add(i, byt[i]);
        }
        return bs;
    }
}

