package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
public class ReadByClass {

    public static void main(String[] args) {
        String html = "<div data-v-42a903eb=\"\" class=\"e-section\">\n" +
                "\t<h3 data-v-42a903eb=\"\" class=\"title-1\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">1 申请理由</h3>\n" +
                "\t<div data-v-42a903eb=\"\" contenteditable=\"true\" id=\"title2\" data-placeholder=\"\" class=\"pell-content\">\n" +
                "      <p>中文</p>\n" +
                "      <p>8899</p>\n" +
                "      <p>5566</p>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "<div data-v-42a903eb=\"\" class=\"e-section\">\n" +
                // 查出此元素
                "\t<h3 data-v-42a903eb=\"\" class=\"title-2\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">2 维修/装修内容</h3>\n" +
                "\t<div data-v-42a903eb=\"\" contenteditable=\"true\" id=\"editorContent1\" data-placeholder=\"\" class=\"pell-content\">xxx</div>\n" +
                "</div>\n" +
                "<div data-v-42a903eb=\"\" class=\"e-section\">\n" +
                "\t<h3 data-v-42a903eb=\"\" class=\"title-3\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">3 施工要求或工期要求</h3>\n" +
                "\t<div data-v-42a903eb=\"\" contenteditable=\"true\" id=\"editorContent2\" data-placeholder=\"\" class=\"pell-content\">xxx</div>\n" +
                "</div>";

        // 解析 HTML 字符串
        Document doc = Jsoup.parse(html);
        // 根据 Class 查找特定的元素
        Elements title2 = doc.getElementsByClass("title-2");

        if (title2.isEmpty()) {
            System.out.println("title2 is null");
            return;
        }

        // 获取该元素内的纯文本内容
        String textContent = title2.get(0).text();
        // 打印结果
        System.out.println("title2 Text: " + textContent);
    }

}
