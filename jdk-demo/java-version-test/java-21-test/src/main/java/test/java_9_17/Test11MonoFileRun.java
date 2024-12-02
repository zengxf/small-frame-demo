package test.java_9_17;

/**
 * Java 11 单文件运行
 * <p>
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestMonofileRun.java
 * </pre>
 * <p>
 * Created by zengxf on 2021/3/30.
 */
public class Test11MonoFileRun {

    public static void main(String[] arr) {
        System.out.println("OK");
        System.out.println(StrUtils.reverse("OK"));
    }

    static class StrUtils {
        static String reverse(String value) {
            var reverse = new StringBuilder()
                    .append(value)
                    .reverse()
                    .toString();
            return reverse;
        }
    }

}
