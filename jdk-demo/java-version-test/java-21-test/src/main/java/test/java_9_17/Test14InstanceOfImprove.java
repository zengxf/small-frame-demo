package test.java_9_17;

/**
 * Java 14 instanceof 增强
 * <p>
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestInstanceOfImprove.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class Test14InstanceOfImprove {

    public static void main(String[] arr) {
        Object obj = "string ==";
//        Object obj = 12;
        if (obj instanceof String str) {
            System.out.println(str);
        } else {
            System.out.println("non");
        }
    }

}
