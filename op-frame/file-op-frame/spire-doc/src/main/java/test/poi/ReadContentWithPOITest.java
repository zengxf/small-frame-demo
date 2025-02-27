package test.poi;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 读取 Docx 内容
 * <p>
 * 读取非常好，
 * 能跟看到和复制的一样，换行效果非常好
 * <p/>
 * ZXF 创建于 2025/2/27
 */
public class ReadContentWithPOITest {

    public static void main(String[] args) {
        String docPath = "D:/Data/Test/doc/aa.docx";
        try (FileInputStream fis = new FileInputStream(docPath); XWPFDocument doc = new XWPFDocument(fis)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String text = extractor.getText();
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
