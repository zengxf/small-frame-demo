package cn.zxf.test.base.stack_trace;

import java.util.Set;
import java.util.stream.Stream;

import static java.lang.StackWalker.Option.*;
import static java.lang.StackWalker.Option;

/**
 * Created by zengxf on 2018/9/13.
 */
public class TestCallerClass {
    public static void main(String[] args) {
        m1(Set.of());
        m1(Set.of(RETAIN_CLASS_REFERENCE, SHOW_REFLECT_FRAMES));
        try {
            Class<?> cls = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
            System.out.println("In main method, Caller Class: " + cls.getName());
        } catch (IllegalCallerException e) {
            System.out.println("In main method, Exception: " + e.getMessage());
        }
    }

    public static void m1(Set<Option> options) {
        m2(options);
    }

    public static void m2(Set<Option> options) {
        try {
            TestCallerClass.class.getMethod("m3", Set.class).invoke(null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void m3(Set<Option> options) {
        try {
            Class<?> cls = StackWalker.getInstance(options).getCallerClass();
            System.out.println("Caller Class: " + cls.getName());
        } catch (UnsupportedOperationException e) {
            System.out.println("Inside m3(): " + e.getMessage());
        }
    }

}
