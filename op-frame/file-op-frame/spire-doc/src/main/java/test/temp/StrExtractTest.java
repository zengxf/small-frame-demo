package test.temp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符匹配提取测试
 * <p/>
 * ZXF 创建于 2025/3/4
 */
public class StrExtractTest {

    public static void main(String[] args) {
        String str = """
                test1 **aa**bb;
                test2 **cc****dd**ee;
                """;

        Pattern pattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
        Matcher matcher = pattern.matcher(str);
        List<String> result = new ArrayList<>();

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        System.out.println(result); // 输出 [aa, cc, dd]
    }


}
