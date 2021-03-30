package test_java16;

/**
 * Java 13 文本块
 * <p>
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestTextBlock.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class TestTextBlock {

    public static void main(String[] arr) {
        String json = """
                {
                    "name":"mkyong",
                    "age":38
                }
                """;
        System.out.print("=");
        System.out.print(json);
        System.out.print("=");
    }

}
