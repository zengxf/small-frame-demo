package tool.watermark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 工具类
 * <p/>
 * ref: https://blog.csdn.net/qq_26383975/article/details/125996277
 * <p/>
 * Created by ZXFeng on 2024/6/25
 */
public class ImgUtils {

    public static void handle(ImgBO bo) throws IOException {
        if (bo == null || bo.text == null || bo.text.isEmpty()) {
            System.err.println("BO 对象无效");
            return;
        }

        // 读取原图片信息
        File srcImgFile = new File(bo.srcPath);
        // 将文件对象转化为图片对象
        Image srcImg = ImageIO.read(srcImgFile);
        // 获取图片的宽
        int srcImgWidth = srcImg.getWidth(null);
        // 获取图片的高
        int srcImgHeight = srcImg.getHeight(null);

        System.out.println("图片的宽: " + srcImgWidth);
        System.out.println("图片的高: " + srcImgHeight);

        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        // 加水印
        //创建画笔
        Graphics2D g = bufImg.createGraphics();
        //srcImg 为上面获取到的原始图片的图片对象
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
        //根据图片的背景设置水印颜色
        g.setColor(new Color(0, 0, 0, 50));
        //设置字体  画笔字体样式为微软雅黑，加粗，文字大小为60pt
        g.setFont(new Font("微软雅黑", Font.BOLD, 60));
        //设置水印的坐标
        int x = (srcImgWidth - getWatermarkLength(bo.text, g)) / 2;
        int y = srcImgHeight / 2;
        // 画出水印 第一个参数是水印内容，第二个参数是x轴坐标，第三个参数是y轴坐标
        g.drawString(bo.text, x, y);
        g.dispose();

        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(bo.outPath);
        ImageIO.write(bufImg, "png", outImgStream);
        outImgStream.flush();
        outImgStream.close();

        System.out.println("添加水印完成");
    }

    /**
     * 获取水印文字的长度
     */
    private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

}
