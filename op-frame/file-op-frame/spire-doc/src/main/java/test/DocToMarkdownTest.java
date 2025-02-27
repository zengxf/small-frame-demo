package test;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;

import java.awt.*;

/**
 * 替换高亮显示的文本 -> Markdown -> 加粗
 * <p/>
 * ZXF 创建于 2025/2/26
 */
public class DocToMarkdownTest {

    public static void main(String[] args) {
        String docPath = "D:/Data/Test/doc/aa.docx";
        String mdPath = "D:/Data/Test/doc/aa.md";
        toMarkdown(docPath, mdPath);
    }

    static void toMarkdown(String docPath, String mdPath) {
        String mdContent = readContent(docPath);

        Document doc = new Document();
        doc.loadFromFile(docPath);

        // 遍历段落
        for (Object item : doc.getSections().get(0).getParagraphs()) {
            Paragraph para = (Paragraph) item;
            for (Object obj : para.getChildObjects()) {
                if (obj instanceof TextRange) {
                    TextRange textRange = (TextRange) obj;
                    String text = textRange.getText();

                    Color highlightColor = textRange.getCharacterFormat().getHighlightColor();

                    // 判断是否存在高亮（默认无色时 RGB 为 0）
                    if (highlightColor.getRGB() != 0
                            && text.length() >= 5
                    ) {
                        System.out.println("文本: " + text);
                        System.out.println("高亮颜色: " + highlightColor);
                        System.out.println();
                        mdContent += StrUtil.format("**{}**", text);
                    } else {
                        mdContent += text;
                    }
                }
            }
        }

        System.out.println(mdContent.length());
        new FileWriter(mdPath).write(mdContent);
    }

    static String readContent(String docPath) {
        Document doc = new Document();
        doc.loadFromFile(docPath, FileFormat.Docx);
        String content = doc.getText();
        doc.close();
        return content;
    }

}
