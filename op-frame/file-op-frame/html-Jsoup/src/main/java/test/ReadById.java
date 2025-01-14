package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * <p/>
 * ZXF 创建于 2025/1/14
 */
public class ReadById {

    public static void main(String[] args) {
        String html = "<div data-v-42a903eb=\"\" class=\"e-section\">\n" +
                "\t<h3 data-v-42a903eb=\"\" class=\"title\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">1 申请理由</h3>\n" +
                // 查出此元素
                "\t<div data-v-42a903eb=\"\" contenteditable=\"true\" id=\"editorContent0\" data-placeholder=\"\" class=\"pell-content\">\n" +
                "      <p>中文测试</p>\n" +
                "      <p>5566</p>\n" +
                "      <p>7788</p>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "<div data-v-42a903eb=\"\" class=\"e-section\">\n" +
                "\t<h3 data-v-42a903eb=\"\" class=\"title\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">2 维修/装修内容</h3>\n" +
                "\t<div data-v-42a903eb=\"\" contenteditable=\"true\" id=\"editorContent1\" data-placeholder=\"\" class=\"pell-content\">xxx</div>\n" +
                "</div>\n" +
                "<div data-v-42a903eb=\"\" class=\"e-section\">\n" +
                "\t<h3 data-v-42a903eb=\"\" class=\"title\" style=\"color: rgb(149, 113, 233); font-size: 14px; line-height: 18px;\">3 施工要求或工期要求</h3>\n" +
                "\t<div data-v-42a903eb=\"\" contenteditable=\"true\" id=\"editorContent2\" data-placeholder=\"\" class=\"pell-content\">xxx</div>\n" +
                "</div>";

        // 解析HTML字符串
        Document doc = Jsoup.parse(html);
        // 根据ID查找特定的元素
        Element editorContent0 = doc.getElementById("editorContent0");

        if (editorContent0 == null) {
            System.out.println("editorContent0 is null");
            return;
        }

        // 获取该元素内的纯文本内容
        String textContent = editorContent0.text();
        // 打印结果
        System.out.println("Editor Content 0 Text: " + textContent);
    }

}
