package test;

import com.spire.doc.*;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;

import java.awt.Color;

/**
 * 读取设置高亮显示的文本
 * <p/>
 * Created by ZXFeng on 2023/11/22
 */
public class ReadHighlightTest {

    public static void main(String[] args) {
        Document doc = new Document();
        doc.loadFromFile("D:/Data/Test/doc/aa.docx");

        // 遍历段落
        for (Object item : doc.getSections().get(0).getParagraphs()) {
            Paragraph para = (Paragraph) item;
            for (Object obj : para.getChildObjects()) {
                if (obj instanceof TextRange) {
                    TextRange textRange = (TextRange) obj;
                    Color highlightColor = textRange.getCharacterFormat().getHighlightColor();

                    // 判断是否存在高亮（默认无色时 RGB 为 0）
                    if (highlightColor.getRGB() != 0) {
                        System.out.println("文本: " + textRange.getText());
                        System.out.println("高亮颜色: " + highlightColor);
                        System.out.println();
                    }
                }
            }
        }
    }

}
