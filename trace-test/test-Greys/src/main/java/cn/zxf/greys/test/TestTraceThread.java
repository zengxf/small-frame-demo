package cn.zxf.greys.test;

import java.lang.management.ManagementFactory;
import java.util.Random;

/**
 * <pre>
 * 启动: 
 * java -cp test-greys.jar cn.zxf.greys.test.TestTraceThread
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-11-06
 */
public class TestTraceThread {

    public static void main( String[] args ) throws InterruptedException {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        Runnable r = () -> {
            while ( true ) {
                System.out.printf( "%s -> %tT.%<tL %n", Thread.currentThread().getName(), System.currentTimeMillis() );
                try {
                    test();
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        };
        new Thread( r, "test-1" ).start();
        new Thread( r, "test-2" ).start();
    }

    public static void test() throws InterruptedException {
        Thread.sleep( 500 + new Random().nextInt( 100 ) );
        if ( new Random().nextInt( 100 ) % 2 == 0 )
            test1();
        else
            test2();
        test12();
    }

    public static void test1() throws InterruptedException {
        Thread.sleep( 700 + new Random().nextInt( 200 ) );
        test10();
    }

    public static void test2() throws InterruptedException {
        Thread.sleep( 1000 + new Random().nextInt( 300 ) );
        test10();
    }

    public static void test10() throws InterruptedException {
        Thread.sleep( 4 + new Random().nextInt( 6 ) );
    }

    public static void test12() throws InterruptedException {
        Thread.sleep( 10 + new Random().nextInt( 20 ) );
    }

}
