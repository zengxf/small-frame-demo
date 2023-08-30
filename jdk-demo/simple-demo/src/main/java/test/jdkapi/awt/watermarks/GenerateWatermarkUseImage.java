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
 * 用图片生成水印
 * <p>
 * Created by zengxf on 2019-04-16
 */
public class GenerateWatermarkUseImage {

    public static void main(String[] args) throws IOException {
        String targetPath = "C:\\Users\\Administrator\\Desktop\\aa";
        String outPath = targetPath + "\\xx";
        String logo = "C:\\Users\\Administrator\\Desktop\\aa\\xx\\logo2.png";
        File logoPath = Paths.get(logo)
                .toFile();
        mkdir(outPath);

        Files.find(Paths.get(targetPath), 1, (p, a) -> {
                    boolean endsWith = !Files.isDirectory(p) && p.getFileName()
                            .toString()
                            .endsWith(".jpg");
                    return endsWith;
                })
                .forEach(jpg -> {
                    String fileName = jpg.getFileName()
                            .toString();

                    System.out.println(jpg.toString() + "\t" + fileName);

                    File srcImgFile = jpg.toFile();
                    File outImageFile = Paths.get(outPath, fileName)
                            .toFile();
                    createWaterMarkByIcon(srcImgFile, logoPath, outImageFile);
                });

    }

    static void createWaterMarkByIcon(File srcImageFile, File logoImageFile, File outputImageFile) {
        float alpha = 1.0f;

        OutputStream os = null;
        try {
            Image logoImg = new ImageIcon(ImageIO.read(logoImageFile)).getImage();
            Image srcImg = ImageIO.read(srcImageFile);
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);

            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = buffImg.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha)); // 透明度

            // 图片水印
            int betaW = (int) (logoImg.getWidth(null) * 1.5);
            int betaH = (int) (logoImg.getHeight(null) * 1.5);
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
