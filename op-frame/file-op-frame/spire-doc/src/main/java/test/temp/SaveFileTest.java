package test.temp;

import cn.hutool.core.io.file.FileWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * 将 Markdown 加粗的提取到笔记里面去
 * <p/>
 * ZXF 创建于 2025/3/4
 */
public class SaveFileTest {

    public static void main(String[] args) {
        String notePath = "D:/Data/Test/doc/test.md";

        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("\n");
        list.add("def");
        list.add("");
        list.add("ghi");

        new FileWriter(notePath).writeLines(list);
    }

}
