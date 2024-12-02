package test.java_9_17;

/**
 * Java 14 record 关键字，类似 lomobok 插件。<br>
 * IDE 还不支持。
 * <p>
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestRecord.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class Test14Record {

    public static void main(String[] arr) {
        System.out.println(new Person("Z", "xf"));
    }

    record Person(String firstName, String lastName) {
    }

}
