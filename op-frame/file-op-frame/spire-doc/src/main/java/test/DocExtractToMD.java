package test;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;

import java.awt.*;

/**
 * 将 Word 高亮部分提取到 Markdown，其他内容直接丢掉
 * <p/>
 * ZXF 创建于 2025/7/28
 */
public class DocExtractToMD {

    public static void main(String[] args) {
        String docPath = "D:/Data/Test/doc/aa.doc"; // doc, docx
        String mdPath = "D:/Data/Test/doc/aa.md";
        toMarkdown(docPath, mdPath);
    }

    static void toMarkdown(String docPath, String mdPath) {
        String mdContent = "";

        Document doc = new Document();
        doc.loadFromFile(docPath);

        // 遍历段落
        for (Object item : doc.getSections().get(0).getParagraphs()) {
            Paragraph para = (Paragraph) item;
            for (Object obj : para.getChildObjects()) {
                if (obj instanceof TextRange textRange) {
                    String text = textRange.getText();

                    Color highlightColor = textRange.getCharacterFormat().getHighlightColor();

                    // 判断是否存在高亮（默认无色时 RGB 为 0）
                    if (highlightColor.getRGB() != 0
                            && text.length() >= 5
                    ) {
                        System.out.println("文本: " + text);
                        System.out.println("高亮颜色: " + highlightColor);
                        System.out.println();
                        mdContent += text + "\n\n";
                    }
                }
            }
        }

        System.out.println(mdContent.length());
        new FileWriter(mdPath).write(mdContent);
    }

}
