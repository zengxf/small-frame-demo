package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
public class ReadByXPath {

    public static void main(String[] args) {
        String html = "" +
                "<div class=\"root\">\n" +
                "    <div class=\"e-section-1\">\n" +
                "        <h3 class=\"title-1\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">1 申请理由</h3>\n" +
                "        <div id=\"editorContent0\" data-placeholder=\"\" class=\"pell-content\">\n" +
                "            <p>中文</p>\n" +
                "            <p>5566</p>\n" +
                "        </div>\n" +
                // 查出此元素
                "        <div id=\"editorContent10\" data-placeholder=\"\" class=\"pell-content\">\n" +
                "            <p>中文--2</p>\n" +
                "            <p>8899--66</p>\n" +
                "            <p>5566--88</p>\n" +
                "        </div>\n" +
                "        <div id=\"editorContent101\" data-placeholder=\"\" class=\"pell-content\">\n" +
                "            <p>XX</p>\n" +
                "            <p>BB</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"e-section-2\">\n" +
                "        <h3 class=\"title-2\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">2 维修/装修内容</h3>\n" +
                "        <div contenteditable=\"true\" id=\"editorContent12\" data-placeholder=\"\" class=\"pell-content\">xxx</div>\n" +
                "    </div>\n" +
                "</div>";

        // System.out.println(html);

        // 解析 HTML 字符串
        Document doc = Jsoup.parse(html);
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
