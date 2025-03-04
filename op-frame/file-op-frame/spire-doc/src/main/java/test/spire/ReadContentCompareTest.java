package test.spire;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Console;
import test.DocToMarkdown;

/**
 * 读取 Docx 内容并比较手动复制的
 * <p>
 * 结果：内容一致，只是手动复制的少了最后一行
 * <p/>
 * ZXF 创建于 2025/2/27
 */
public class ReadContentCompareTest {

    public static void main(String[] args) {
        String docPath = "D:/Data/Test/doc/aa.docx";
        String mdPath = "D:/Data/Test/doc/bb.md";
        String md2Path = "D:/Data/Test/doc/cc.md";

        String mdContent = new FileReader(mdPath).readString();
        String docContent = DocToMarkdown.readContent(docPath);
        new FileWriter(md2Path).write(docContent);

        Console.log("mdContent.length: [{}]", mdContent.length());      // 手动复制的少一行
        Console.log("docContent.length: [{}]", docContent.length());    // 多一行
        Console.log("eq: [{}]", mdContent.equals(docContent));
        Console.log("trim-eq: [{}]", mdContent.trim().equals(docContent.trim()));
    }

}
