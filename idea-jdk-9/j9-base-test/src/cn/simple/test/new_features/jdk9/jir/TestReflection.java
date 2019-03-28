package cn.simple.test.new_features.jdk9.jir;

/**
 * JDK 内部使用的，不能调用
 * <p>
 * module-info.java 声明如下所示：
 * <pre>
 * exports jdk.internal.reflect to java.sql, jdk.dynalink, java.logging, java.sql.rowset, jdk.unsupported, jdk.scripting.nashorn;
 * </pre>
 * <p>
 * Created by zengxf on 2017/10/10.
 */
@Deprecated(forRemoval = true)
public class TestReflection {

    public static void main(String[] arr) {
        test1();
    }

    static void test1() {
        System.out.println("==================");
//            System.out.println(Reflection.getCallerClass());
    }

}
