package test.thread;

public class TestThreadContextClassLoader {

    public static void main( String[] args ) {
        ClassLoader cl = new Thread( () -> {
        } ).getContextClassLoader(); // 在 new 的时候会初始化
        System.out.println( cl );
    }

}
