package test.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 印章生成器
 * <p>
 * 在线生成 https://tools.kalvinbg.cn/convenience/seal
 * <p>
 * https://chatgpt.com/c/6930f35f-b6fc-8324-bc51-df11fa580205
 */
public class OfficialSeal {

    static String FONT_NAME = "";
    static double SPACING_FACTOR = 1.25;

    static {
        FONT_NAME = "SimHei"; // Windows 黑体
        FONT_NAME = "宋体";
        FONT_NAME = "NSimSun";            // Windows 新宋体
        FONT_NAME = "KaiTi";            // Windows 楷体
        // FONT_NAME = "FangSong";          // Windows 仿宋
    }

    public static void generateSeal(String mainText, String bottomText,
                                    int diameter, String outputFile) {
        try {
            int radius = diameter / 2;
            BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Color red = new Color(220, 0, 0);
            g2d.setColor(red);
            g2d.setStroke(new BasicStroke(diameter / 35f));
            g2d.drawOval(5, 5, diameter - 10, diameter - 10);

            // 中心五角星
            drawStar(g2d, radius, radius, diameter / 8, red);

            // 上弧文字
            double radius1 = radius * 0.75 - 5;     // -5 是为了离边框远些
            drawArcText(g2d, mainText, radius, radius, radius1, diameter, SPACING_FACTOR);

            // 下方水平文字
            if (bottomText != null && !bottomText.isEmpty()) {
                Font bottomFont = new Font(FONT_NAME, Font.BOLD, diameter / 10);
                g2d.setFont(bottomFont);
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(bottomText);
                int y = (int) (radius + radius * 0.55);
                g2d.drawString(bottomText, radius - textWidth / 2, y);
            }

            g2d.dispose();
            ImageIO.write(image, "png", new File(outputFile));
            System.out.println("印章已生成: " + outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void drawStar(Graphics2D g2d, double cx, double cy, double r, Color color) {
        double angle = Math.toRadians(-18);
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];
        for (int i = 0; i < 10; i++) {
            double radius = (i % 2 == 0) ? r : r / 2.5;
            xPoints[i] = (int) Math.round(cx + radius * Math.cos(angle));
            yPoints[i] = (int) Math.round(cy + radius * Math.sin(angle));
            angle += Math.PI / 5;
        }
        g2d.fillPolygon(xPoints, yPoints, 10);
    }

    /**
     * 绘制上弧文字（自然顺序 + 可调字距）
     *
     * @param spacingFactor 字距放大系数（1.0 = 原始，>1.0 = 更稀疏）
     */
    private static void drawArcText(Graphics2D g2d, String text, double cx, double cy,
                                    double radius, int diameter, double spacingFactor) {
        Font font = new Font(FONT_NAME, Font.BOLD, diameter / 10);
        g2d.setFont(font);
        FontRenderContext frc = g2d.getFontRenderContext();

        // 计算总弧长
        double totalArcLen = 0;
        for (char c : text.toCharArray()) {
            totalArcLen += g2d.getFont().getStringBounds(String.valueOf(c), frc).getWidth() * spacingFactor;
        }
        double totalArc = totalArcLen / radius;
        double startAngle = Math.PI / 2 + totalArc / 2;

        for (int i = 0; i < text.length(); i++) {
            String c = String.valueOf(text.charAt(i));
            double charWidth = g2d.getFont().getStringBounds(c, frc).getWidth() * spacingFactor;
            double angle = startAngle - (charWidth / (2 * radius));

            AffineTransform old = g2d.getTransform();
            double x = cx + radius * Math.cos(angle);
            double y = cy - radius * Math.sin(angle);

            g2d.translate(x, y);
            g2d.rotate(Math.PI / 2 - angle);
            g2d.drawString(c, (float) (-charWidth / (2 * spacingFactor)), 0);
            g2d.setTransform(old);

            startAngle -= charWidth / radius;
        }
    }


    public static void main(String[] args) {
        generateSeal("一个有趣科技有限公司", "我是抬头字", 300, "seal_natural.png");
    }

}

