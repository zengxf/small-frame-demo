package test.string.fmt;

import java.io.IOException;
import java.util.Arrays;

/**
 * 追加空格
 * <br/>
 * Created by ZXFeng on 2022/9/26.
 */
public class FileAppendSpace {

    private static final char[] enSymbol;
    private static final char[] cnSymbol;

    static {
        enSymbol = "`~!@#$%^&*()_+{}-=[];'\\:\"|,./<>?`\r\n ".toCharArray();
        cnSymbol = "—、（），。“”：；？".toCharArray();
        Arrays.sort(enSymbol);
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
            boolean preEn = isSingle(pre);
            boolean curEn = isSingle(cur);
            if (!preEn) {     // 前面是中文
                if (curEn && cur != ' ' && !isSymbol(cur)) {  // 现在是英文，在英文前面加一个空格
                    sb.append(" ");
                    sb.append(cur);
                    added = true;
                }
            }
            if (preEn) {      // 前面是英文
                if (!curEn && pre != ' ' && !isSymbol(pre)) { // 现在是中文，在英文后面加一个空格
                    sb.append(" ");
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


    // 判断是不是单字符
    static boolean isSingle(char c) {
        return (c >= '0' && c <= '9')
                || (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || Arrays.binarySearch(enSymbol, c) >= 0
                || Arrays.binarySearch(cnSymbol, c) >= 0;
    }

    static boolean isSymbol(char c) {
        return Arrays.binarySearch(enSymbol, c) >= 0
                || Arrays.binarySearch(cnSymbol, c) >= 0;
    }

}
