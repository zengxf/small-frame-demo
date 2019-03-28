package cn.zxf.greys.test;

import java.lang.management.ManagementFactory;

/**
 * <pre>
 * 启动: 
 * java -cp test-greys.jar cn.zxf.greys.test.TestConnection
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-11-06
 */
public class TestConnection {

    public static void main( String[] args ) throws InterruptedException {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        while ( true ) {
            System.out.printf( "%tT %n", System.currentTimeMillis() );
            Thread.sleep( 2000L );
        }
    }

}
