package test_java17;

/**
 * Java 14 空指针异常精准提示
 * <p>
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestNullPointerException.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class TestNullPointerException {

    public static void main(String[] arr) {
        C c = new C();
        c.b = new B();
        System.out.println(c.b.a.sign);
    }

    static class A {
        String sign;
    }

    static class B {
        A a;
    }

    static class C {
        B b;
    }
}
