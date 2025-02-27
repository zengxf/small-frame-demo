package test.spire;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import com.spire.doc.Document;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;

import java.awt.*;

/**
 * 替换高亮显示的文本 -> Markdown -> 加粗
 * <p/>
 * ZXF 创建于 2025/2/26
 */
@Deprecated(since = "太麻烦了，要先创建 md 文件并复制内容到 md 里面去")
public class ReplaceHighlightToMdTest {

    public static void main(String[] args) {
        String mdPath = "D:/Data/Test/doc/aa.md";
        String docPath = "D:/Data/Test/doc/aa.docx";

        String mdContent = new FileReader(mdPath).readString();
        System.out.println(mdContent.length());

        Document doc = new Document();
        doc.loadFromFile(docPath);

        // 遍历段落
        for (Object item : doc.getSections().get(0).getParagraphs()) {
            Paragraph para = (Paragraph) item;
            for (Object obj : para.getChildObjects()) {
                if (obj instanceof TextRange) {
                    TextRange textRange = (TextRange) obj;
                    Color highlightColor = textRange.getCharacterFormat().getHighlightColor();

                    // 判断是否存在高亮（默认无色时 RGB 为 0）
                    if (highlightColor.getRGB() != 0) {
                        String text = textRange.getText();
                        if (text.length() < 5) {
                            continue;
                        }
                        System.out.println("文本: " + text);
                        System.out.println("高亮颜色: " + highlightColor);
                        System.out.println();
                        mdContent = mdContent.replace(text, StrUtil.format("**{}**", text));
                    }
                }
            }
        }

        System.out.println(mdContent.length());
        new FileWriter(mdPath).write(mdContent);
    }

}
