package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
public class ReadFromFile {

    public static void main(String[] args) throws IOException {
        String fileName = "/test1.html";
        // System.out.println(ReadFromFile.class.getResource(fileName).getFile());

        File file = new File(ReadFromFile.class.getResource(fileName).getFile());
        Document doc = Jsoup.parse(file, "UTF-8");

        // System.out.println(doc);

        // 根据 Class 查找特定的元素
        Elements divC10 = doc.selectXpath("//div[@id='editorContent10']");

        if (divC10.isEmpty()) {
            System.out.println("divC10 is null");
            return;
        }

        // 获取该元素内的纯文本内容
        String textContent = divC10.text();
        // 打印结果
        System.out.println("divC10 Text: " + textContent);
    }

}
