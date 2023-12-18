package cn.zxf.test.base.stack_trace;

import java.util.Set;
import java.util.stream.Stream;

import static java.lang.StackWalker.Option.*;

/**
 * Created by zengxf on 2018/9/13.
 */
public class TestStackWalker {

    public static void main(String[] args) {
        m1(Set.of());
        System.out.println();
        m1(Set.of(RETAIN_CLASS_REFERENCE, SHOW_REFLECT_FRAMES));
    }

    public static void m1(Set<StackWalker.Option> options) {
        m2(options);
    }

    public static void m2(Set<StackWalker.Option> options) {
        try {
            System.out.println("Using StackWalker Options: " + options);
            TestStackWalker.class.getMethod("m3", Set.class).invoke(null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void m3(Set<StackWalker.Option> options) {
        StackWalker.getInstance(options).walk(TestStackWalker::processStack);
    }

    public static Void processStack(Stream<StackWalker.StackFrame> stack) {
        stack.forEach(frame -> {
            int bci = frame.getByteCodeIndex();
            String className = frame.getClassName();
            Class<?> classRef = null;
            try {
                classRef = frame.getDeclaringClass();
            } catch (UnsupportedOperationException e) {
            }
            String fileName = frame.getFileName();
            int lineNumber = frame.getLineNumber();
            String methodName = frame.getMethodName();
            boolean isNative = frame.isNativeMethod();
            StackTraceElement sfe = frame.toStackTraceElement();
            System.out.printf("Native Method=%b", isNative);
            System.out.printf(", Byte Code Index=%d", bci);
            System.out.printf(", Module Name=%s", sfe.getModuleName());
            System.out.printf(", Module Version=%s", sfe.getModuleVersion());
            System.out.printf(", Class Name=%s", className);
            System.out.printf(", Class Reference=%s", classRef);
            System.out.printf(", File Name=%s", fileName);
            System.out.printf(", Line Number=%d", lineNumber);
            System.out.printf(", Method Name=%s.%n", methodName);
        });
        return null;
    }

}
