package test;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将 Markdown 加粗的提取到笔记里面去
 * <p/>
 * ZXF 创建于 2025/3/4
 */
public class MarkdownToNote {

    public static void main(String[] args) {
        String mdPath = "D:/Data/Test/doc/a3.md";
        String notePath = mdPath + ".nt.md";

        String str = new FileReader(mdPath).readString();

        Pattern pattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
        Matcher matcher = pattern.matcher(str);
        List<String> result = new ArrayList<>();

        while (matcher.find()) {
            result.add(matcher.group(1));
            result.add("");
        }

        new FileWriter(notePath).writeLines(result);
    }

}
