package test.spire;

import test.DocToMarkdownTest;

/**
 * 读取 Docx 内容
 * <p>
 * 读取非常好，
 * 能跟看到和复制的一样，换行效果非常好
 * <p/>
 * ZXF 创建于 2025/2/27
 */
public class ReadContentTest {

    public static void main(String[] args) {
        String docPath = "D:/Data/Test/doc/aa.docx";
        String text = DocToMarkdownTest.readContent(docPath);
        System.out.println(text);
    }

}
