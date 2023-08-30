package test.jvm.jit;

/**
 * <pre>
 * -XX:+PrintCompilation 
 * -XX:+UnlockDiagnosticVMOptions
 * -XX:+PrintInlining
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-02-02
 */
public class TestPrintCompilation {
    public static void main( String[] args ) {
        test();
    }

    static void test() {
        while ( true ) {
            test122();
        }
    }

    static void test122() {
        Math.random();
    }
}
