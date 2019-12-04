package test.test_utils;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

/**
 * 参考：https://juejin.im/post/5d1b5531f265da1bc23f91a3
 * <p>
 * Created by zengxf on 2019-12-04
 */
public class FastThreadLocalTest {
    private static FastThreadLocal<Integer> fastThreadLocal = new FastThreadLocal<>();

    public static void main( String[] args ) {
        // 使用 FastThreadLocalThread 更优，普通线程也可以
        new FastThreadLocalThread( () -> {
            for ( int i = 0; i < 100; i++ ) {
                fastThreadLocal.set( i );
                System.out.println( Thread.currentThread()
                        .getName() + "====" + fastThreadLocal.get() );
                try {
                    Thread.sleep( 50 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        }, "fastThreadLocal1" ).start();

        new FastThreadLocalThread( () -> {
            for ( int i = 0; i < 100; i++ ) {
                System.out.println( Thread.currentThread()
                        .getName() + "====" + fastThreadLocal.get() );
                try {
                    Thread.sleep( 50 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        }, "fastThreadLocal2" ).start();
    }
}
