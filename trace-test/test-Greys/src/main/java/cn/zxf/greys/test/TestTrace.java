package cn.zxf.greys.test;

import java.lang.management.ManagementFactory;

/**
 * <pre>
 * 启动: 
 * java -cp test-greys.jar cn.zxf.greys.test.TestTrace
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-11-06
 */
public class TestTrace {

    public static void main( String[] args ) throws InterruptedException {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        while ( true ) {
            System.out.printf( "%tT %n", System.currentTimeMillis() );
            test();
        }
    }

    public static void test() throws InterruptedException {
        Thread.sleep( 2000L );
    }

}
