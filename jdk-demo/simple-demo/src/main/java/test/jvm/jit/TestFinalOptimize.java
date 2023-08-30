package test.jvm.jit;

/**
 * 反编译看变量的 final 标识是否还在
 * <p>
 * 局部变量是不保存 final 标识，因此并不会提高优化性能
 * 
 * <p>
 * Created by zengxf on 2017-10-16
 */
public class TestFinalOptimize {

    static final String NAME = "zxf";

    final String        name = "feng";

    public static void main( String[] args ) {
        final String address = "lh";
        System.out.println( address );
    }

    void test() {
        final String address = "lh";
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println( address );
            }
        };
        r.run();
    }
}
