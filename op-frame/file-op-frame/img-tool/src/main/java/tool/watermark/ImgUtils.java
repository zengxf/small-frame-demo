package tool.watermark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 工具类
 * <p/>
 * ref: https://blog.csdn.net/qq_35165004/article/details/82186125
 * <p/>
 * Created by ZXFeng on 2024/6/25
 */
public class ImgUtils {

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     */
    public static void imageByText(ImgBO bo) throws Exception {
        Font font = new Font(bo.fontName, bo.fontStyle, bo.fontSize);   // 水印文字字体

        ImageObserver observer = null;
        Image srcImg = ImageIO.read(new File(bo.srcPath));  // 源图片
        int width = srcImg.getWidth(observer);              // 原图宽度
        int height = srcImg.getHeight(observer);            // 原图高度
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = buffImg.createGraphics();            // 得到画笔对象
        // 设置对线段的锯齿状边缘处理
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

        // 设置水印旋转
        g.rotate(Math.toRadians(bo.degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
        g.setColor(bo.fontColor);   // 设置水印文字颜色
        g.setFont(font);            // 设置水印文字字体
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, bo.alpha));  // 设置水印文字透明度

        int x = -width / 2;
        int markWidth = bo.fontSize * getTextLength(bo.text);   // 字体长度
        int markHeight = bo.fontSize;   // 字体高度

        while (x < width * 1.5) {       // 循环添加水印
            int y = -height / 2;
            while (y < height * 1.5) {
                g.drawString(bo.text, x, y);
                y += markHeight + bo.yInterval;
            }
            x += markWidth + bo.xInterval;
        }
        g.dispose();                    // 释放资源

        // 生成图片
        int dotLastIndex = bo.srcPath.lastIndexOf('.');
        String extension = bo.srcPath.substring(dotLastIndex + 1);
        OutputStream os = new FileOutputStream(bo.outPath);
        ImageIO.write(buffImg, extension, os);
        os.close();

        System.out.println("添加水印文字成功!");
    }

    /**
     * 获取文本长度。汉字为 1:1，英文和数字为 2:1
     */
    private static int getTextLength(String text) {
        int length = text.length();
        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }

}
