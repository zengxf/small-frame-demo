package tool.watermark;

import java.awt.*;

/**
 * <p/>
 * Created by ZXFeng on 2024/6/25
 */
public class ImgBO {

    public String srcPath;          // 源地址

    public String text = "FA";      // 水印文字

    public float alpha = 0.5f;                  // 字体透明度
    public String fontName = "微软雅黑";         // 字体名
    public int fontSize = 28;                   // 字体大小
    public int fontStyle = Font.BOLD;           // 字体样式
    public Color fontColor = Color.lightGray;   // 字体颜色
    public int degree = -40;                    // 旋转度数
    public int xInterval = 120;                 // 水印之间的间隔
    public int yInterval = 120;                 // 水印之间的间隔

    public String outPath;          // 输出地址

}
