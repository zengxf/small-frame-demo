package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
public class ReadBySelector {

    public static void main(String[] args) {
        String html = "<div  class=\"e-section-1\">\n" +
                "\t<h2 data-v-42a903eb=\"\" class=\"title-1\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">1 申请理由</h2>\n" +
                "\t<div id=\"editorContent0\" data-placeholder=\"\" class=\"pell-content\">\n" +
                "      <p>中文</p>\n" +
                "      <p>8899</p>\n" +
                "      <p>5566</p>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "\t<div id=\"editorContent10\" data-placeholder=\"\" class=\"pell-content\">\n" +
                "      <p>中文2</p>\n" +
                "      <p>889966</p>\n" +
                "      <p>556688</p>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "<div  class=\"e-section-2\">\n" +
                "\t<h3 data-v-42a903eb=\"\" class=\"title-2\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">2 维修/装修内容</h3>\n" +
                "\t<div data-v-42a903eb=\"\" contenteditable=\"true\" id=\"editorContent1\" data-placeholder=\"\" class=\"pell-content\">xxx</div>\n" +
                "</div>";

        // 解析 HTML 字符串
        Document doc = Jsoup.parse(html);
        // 根据 Class 查找特定的元素
        Elements h2 = doc.selectXpath("h2");

        if (h2.isEmpty()) {
            System.out.println("h2 is null");
            return;
        }

        // 获取该元素内的纯文本内容
        String textContent = h2.get(0).text();
        // 打印结果
        System.out.println("h2 Text: " + textContent);
    }

}
