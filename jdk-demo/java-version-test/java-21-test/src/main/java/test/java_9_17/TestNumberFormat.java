package test.java_9_17;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestNumberFormat.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class TestNumberFormat {

    public static void main(String[] arr) {
        NumberFormat fmt = NumberFormat.getNumberInstance(Locale.SIMPLIFIED_CHINESE);
        String result = fmt.format(1000);
        System.out.println(result);
    }

}
