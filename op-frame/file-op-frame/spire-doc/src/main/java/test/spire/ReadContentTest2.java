package test.spire;

import com.spire.doc.Document;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;

/**
 * 读取 Docx 内容
 * <p>
 * 读取非常好，
 * 能跟看到和复制的一样，换行效果非常好
 * <p/>
 * ZXF 创建于 2025/2/27
 */
@Deprecated(since = "主要是代码太多")
public class ReadContentTest2 {

    public static void main(String[] args) {
        String docPath = "D:/Data/Test/doc/aa.docx";

        // 加载 Word 文档
        Document doc = new Document();
        doc.loadFromFile(docPath);

        // 用于存储完整文本
        StringBuilder fullText = new StringBuilder();

        // 遍历所有 Section
        for (Object sectionObj : doc.getSections()) {
            Section section = (Section) sectionObj;
            // 遍历所有段落
            for (Object paragraphObj : section.getParagraphs()) {
                Paragraph paragraph = (Paragraph) paragraphObj;
                fullText.append(paragraph.getText());
                fullText.append("\n");  // 添加段落换行符（\n）
            }
        }

        // 输出完整内容
        System.out.println(fullText);
    }

}
