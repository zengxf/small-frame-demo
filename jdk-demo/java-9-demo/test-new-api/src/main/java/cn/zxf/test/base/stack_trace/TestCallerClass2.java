package cn.zxf.test.base.stack_trace;

import java.lang.StackWalker.Option;
import java.util.Set;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

/**
 * Created by zengxf on 2018/9/13.
 */
public class TestCallerClass2 {

    public static void main(String[] args) {
        Set<Option> options = Set.of(RETAIN_CLASS_REFERENCE);

        TestCallerClass.m1(options);
        TestCallerClass.m2(options);
        TestCallerClass.m3(options);

        System.out.println("\nCalling the main() method:");
        TestCallerClass.main(null);

        System.out.println("\nUsing an anonymous class:");
        new Object() {
            {
                TestCallerClass.m3(options);
            }
        };

        System.out.println("\nUsing a lambda expression:");
        new Thread(() -> TestCallerClass.m3(options)).start();
    }

}
