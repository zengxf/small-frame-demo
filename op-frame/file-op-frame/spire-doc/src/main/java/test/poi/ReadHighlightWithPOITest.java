package test.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;

/**
 * 读取设置高亮显示的文本
 * <p/>
 * ZXF 创建于 2025/2/26
 */
@Deprecated(since = "读取效果不理想")
public class ReadHighlightWithPOITest {

    public static void main(String[] args) throws Exception {
        String docPath = "D:/Data/Test/doc/aa.docx";
        XWPFDocument doc = new XWPFDocument(new FileInputStream(docPath));

        for (XWPFParagraph para : doc.getParagraphs()) {
            for (XWPFRun run : para.getRuns()) {
                String color = run.getColor();
                // 高亮颜色在 POI 中可能映射为特定值（如黄色对应 "FFFF00"）
                if (color != null) {
                    System.out.println("高亮文本: " + run.getText(0));
                } else {
                    System.out.println(run.getText(0));
                }
            }
        }
    }

}
