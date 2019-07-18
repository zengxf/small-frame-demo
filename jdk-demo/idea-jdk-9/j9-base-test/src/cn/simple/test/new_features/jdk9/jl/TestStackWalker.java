package cn.simple.test.new_features.jdk9.jl;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestStackWalker {

    public static void main(String[] arr) {
        test1();
    }

    static void test1() {
        test2();
    }

    static void test2() {
        Test.test();
    }

    static class Test {
        static void test() {
           TestB.test();
        }
    }

    static class TestB {
        static void test() {
            StackWalker.getInstance()
                    .forEach(frame -> {
                        System.out.println("-----------------");
                        System.out.println(frame.toString());
//                        System.out.println(frame.getClassName());
//                        System.out.println(frame.getMethodName());
//                        System.out.println(frame.getLineNumber());
                    });
            System.out.println("==================");
            System.out.println(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass());
        }
    }

}
