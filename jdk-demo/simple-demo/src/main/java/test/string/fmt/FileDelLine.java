package test.string.fmt;

import java.util.Arrays;

/**
 * 删除未正确结尾的行的换行符
 * <br/>
 * Created by ZXFeng on 2023/1/10.
 */
public class FileDelLine {

    private static final char[] endSymbol;

    static {
        endSymbol = ",.:>，。：；？、".toCharArray();
        Arrays.sort(endSymbol);
    }

    public static void main(String[] args) {
        String str = """
                - 而无状态的 Handler，作为Context的成员，
                - 关联在ChannelHandlerContext中。
                - 一个unsafe（完成）test.
                - ChannelPipeline流水线，所示：abc dd.
                                
                1. 吃饭
                睡觉，玩
                游戏
                                
                2. 运动.
                3. 看书
                                
                - Java Code
                ```java
                System.out.println(str);
                                
                System.out.println(str);
                ```
                                
                #### 4 级标题
                1. 内容.
                2. 内容.
                """;

        System.out.println("---------------------");
        System.out.println(str);

        System.out.println("---------------------");
        String fmt = fmtStr(str);
        System.out.println(fmt);
    }

    static String fmtStr(String str) {
        String[] lines = str.split("\r*\n");
        StringBuilder sb = new StringBuilder(lines.length);

        boolean isCode = false;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.isBlank()) {
                sb.append(line).append("\r\n");
                continue;
            }
            if (!isCode) {
                isCode = line.contains("```");
                if (isCode) {           // code 开始
                    sb.append("\r\n").append(line).append("\r\n");
                    continue;
                }
            } else {
                if (line.contains("```")) {
                    isCode = false;     // code 结束
                }
                sb.append(line).append("\r\n");
                continue;
            }

            char[] arr = line.trim().toCharArray();
            char symbol = arr[arr.length - 1];

            if (isLineEnd(symbol)) {    // 正确换行的
                sb.append(line).append("\r\n");
            } else {
                if (line.startsWith("#"))   // 是标题
                    sb.append(line).append("\r\n");
                else
                    sb.append(line);
            }
        }

        return sb.toString();
    }

    static boolean isLineEnd(char c) {
        return Arrays.binarySearch(endSymbol, c) >= 0;
    }

}
