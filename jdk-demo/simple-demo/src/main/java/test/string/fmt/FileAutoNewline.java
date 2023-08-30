package test.string.fmt;

import java.io.IOException;
import java.util.Arrays;

/**
 * 自动换行
 * <br/>
 * Created by ZXFeng on 2022/9/28.
 */
public class FileAutoNewline {

    private static final char[] cnSymbol;

    static {
        cnSymbol = "，。：；？".toCharArray();
        Arrays.sort(cnSymbol);
    }

    public static void main(String[] args) throws IOException {
        String str = """
                - 而无状态的 Handler，作为Context的成员，
                - 关联在ChannelHandlerContext中。
                - 一个unsafe（完成）test.
                - ChannelPipeline流水线，所示：abc dd
                """;

        System.out.println("---------------------");
        System.out.println(str);

        System.out.println("---------------------");
        String fmt = fmtStr(str);
        System.out.println(fmt);
    }

    static String fmtStr(String str) {
        char[] arr = str.toCharArray();
        StringBuilder sb = new StringBuilder(arr.length);

        char pre = arr[0];
        sb.append(pre);
        for (int i = 1; i < arr.length; i++) {
            char cur = arr[i];
            boolean added = false;
            if (isSymbol(pre)) {    // 前面可换行的符号
                if (cur != '\r') {  // 现在不是换行，在符号后面加一个换行
                    sb.append("\r\n");
                    sb.append(cur);
                    added = true;
                }
            }
            if (!added) {
                sb.append(cur);
            }
            pre = cur;
        }

        return sb.toString();
    }

    static boolean isSymbol(char c) {
        return Arrays.binarySearch(cnSymbol, c) >= 0;
    }

}
