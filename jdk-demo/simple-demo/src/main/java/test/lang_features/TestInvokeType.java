package test.lang_features;

/**
 * 几种函数调用
 * 
 * <p>
 * Created by zengxf on 2018-03-21
 */
// M:\project\zxf_super_demo\simple-demo\bin\test\illustration
public class TestInvokeType {

    public static void testVirtual() {
        new TestInvokeType().testB();
    }

    public static void testStatic() {
        TestInvokeType.testA();
        TestInvokeType.testC();
    }

    public void testSpecial() {
        super.toString();
        // TestInvokeType.testC(); // 静态不算
        this.testD();
        new TestInvokeType();
    }

    public static void testInterface() {
        Runnable r = new MyRunnable();
        r.run();
    }

    public static void testDynamic() {
        Runnable r = () -> {
        };
        r.run();
    }

    public static void testA() {
    }

    public void testB() {
    }

    private static void testC() {
    }

    private void testD() {
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
        }

    }

}
