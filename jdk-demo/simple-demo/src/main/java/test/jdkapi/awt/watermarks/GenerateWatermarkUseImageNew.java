package test.jdkapi.awt.watermarks;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 用递归用图片生成水印（以支持内部文件夹）
 * <p>
 * Created by zengxf on 2019-04-16
 */
public class GenerateWatermarkUseImageNew {

	static String LOGO_PATH = "G:\\水印\\水印1.png";
	static File LOGO_FILE = Paths.get(LOGO_PATH).toFile();
	static String SRC_PATH = "G:\\2019AWlookbook\\2019AWlookbook";
	static String OUT_PATH = SRC_PATH + "_图片水印";

	public static void main(String[] args) throws IOException {
		mkdir(OUT_PATH);

		Files.find(Paths.get(SRC_PATH), 10, (p, a) -> {
			boolean sign = !p.toString().equals(SRC_PATH) && (Files.isDirectory(p) || isImage(p));
			return sign;
		}).forEach(f -> {
			String src = f.toString();
			String disc = src.replace(SRC_PATH, "");
			String out = OUT_PATH + disc;
			if (Files.isDirectory(f)) {
				mkdir(out);
			} else {
				try {
					handleImage(f.toString(), out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		System.out.println("完成!!!!");
	}

	static void handleImage(String src, String out) throws IOException {
		System.out.println("源图 => 输出：\t" + src + " \t " + out);
		File srcImgFile = Paths.get(src).toFile();
		File outImageFile = Paths.get(out).toFile();
		createWaterMarkByIcon(srcImgFile, outImageFile);
	}

	static boolean isImage(Path path) {
		return path.getFileName().toString().toLowerCase().endsWith(".jpg");
	}

	static void createWaterMarkByIcon(File srcImageFile, File outputImageFile) {
		float alpha = 1.0f;

		OutputStream os = null;
		try {
			Image logoImg = new ImageIcon(ImageIO.read(LOGO_FILE)).getImage();
			Image srcImg = ImageIO.read(srcImageFile);
			int width = srcImg.getWidth(null);
			int height = srcImg.getHeight(null);

			BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			Graphics2D graphics = buffImg.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha)); // 透明度

			// 图片水印
			int betaW = (int) (logoImg.getWidth(null) * 1.2);
			int betaH = (int) (logoImg.getHeight(null) * 1.2);
			for (int w = 10; w < width; w += betaW) {
				for (int h = 10; h < height; h += betaH) {
					graphics.drawImage(logoImg, w, h, null);
				}
			}

			graphics.dispose(); // 释放资源
			os = new FileOutputStream(outputImageFile);
			ImageIO.write(buffImg, "JPG", os); // 生成图片
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static void mkdir(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath) && Files.isDirectory(filePath))
				return;
			Files.createDirectory(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
