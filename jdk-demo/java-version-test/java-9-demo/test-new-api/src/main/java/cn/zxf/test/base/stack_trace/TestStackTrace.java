package cn.zxf.test.base.stack_trace;

/**
 * 栈跟踪
 * <p>
 * Created by zengxf on 2018/9/13.
 */
public class TestStackTrace {

    public static void main(String[] args) {
        m1();
    }

    public static void m1() {
        m2();
    }

    public static void m2() {
        System.out.println("\nWithout using reflection: ");
        m3();
        try {
            System.out.println("\nUsing reflection: ");
            TestStackTrace.class.getMethod("m3").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void m3() {
        StackTraceElement[] frames = Thread.currentThread().getStackTrace();
        for (StackTraceElement frame : frames) {
            System.out.println(frame.toString());
        }
    }

}
