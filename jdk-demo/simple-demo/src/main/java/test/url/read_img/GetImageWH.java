package test.url.read_img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 读取图片宽和高
 * 
 * @author zengxf
 */
public class GetImageWH {
    public static void main(String[] args) {
	try {
	    long start = System.currentTimeMillis();
	    for (int i = 0; i < 1; i++) {
		BufferedImage buf = ImageIO.read(new File("redEnv.png"));
		System.out.printf("W: %d, H: %d%n", buf.getWidth(), buf.getHeight());
	    }
	    long useTime = System.currentTimeMillis() - start;
	    System.out.printf("%n用时：%d ms", useTime);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
