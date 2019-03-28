package cn.zxf.test.base.stack_trace;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

/**
 * 权限设置测试
 * <p>
 * Created by zengxf on 2018/9/13.
 */
public class StackWalkerPermissionCheck {

    public static void main(String[] args) {
        System.out.println("Before installing security manager:");
        printStackFrames();
        SecurityManager sm = System.getSecurityManager();
        if (sm == null) {
            sm = new SecurityManager();
            System.setSecurityManager(sm);
        }
        System.out.println("\nAfter installing security manager:");
        printStackFrames();
    }

    public static void printStackFrames() {
        try {
            StackWalker.getInstance(RETAIN_CLASS_REFERENCE)
                    .forEach(System.out::println);
        } catch (SecurityException e) {
            System.out.println("Could not create a StackWalker. Error: " + e.getMessage());
        }
    }
}
