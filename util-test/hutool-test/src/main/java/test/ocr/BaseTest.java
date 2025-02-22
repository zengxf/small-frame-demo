package test.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * <p/>
 * ZXF 创建于 2025/2/22
 */
abstract class BaseTest {

    /**
     * <pre>
     * language: 设置识别语言
     *   eng 英文
     *   chi_sim 中文
     *   eng+chi_sim 英文和中文
     * </pre>
     */
    public static void start(String imgPath, String language) {
        // 图片文件路径
        File imageFile = new File(imgPath);

        // 创建 Tesseract 实例
        ITesseract tesseract = new Tesseract();

        try {
            // 设置语言数据路径（如果放在 resources/tessdata 下，可以省略此步骤）
            tesseract.setDatapath("D:/Data/tess4j/tessdata");

            // 设置识别语言
            tesseract.setLanguage(language);

            tesseract.setOcrEngineMode(1); // 设置OCR引擎模式（OEM）
            tesseract.setPageSegMode(6);   // 设置图片分割模式（PSM）

            // 执行 OCR 识别
            String result = tesseract.doOCR(imageFile);

            // 输出结果
            System.out.println("识别结果：");
            System.out.println("----------------------------------");
            System.out.println(result);
            System.out.println("----------------------------------");
        } catch (TesseractException e) {
            System.err.println("OCR 识别错误: " + e.getMessage());
        }
    }

}
