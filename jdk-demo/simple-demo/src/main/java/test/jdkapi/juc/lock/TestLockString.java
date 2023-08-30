package test.jdkapi.juc.lock;

import lombok.extern.slf4j.Slf4j;
import util.SleepUtils;

/**
 * 测试以字符串为对象的锁
 * 
 * <p>
 * Created by zengxf on 2018-06-22
 */
@Slf4j
public class TestLockString {

    public static void main( String[] args ) throws InterruptedException {
        Runnable r1 = () -> {
            String key = fmt( 1, "zxf" );
            testString( key );
        };
        Runnable r2 = () -> {
            String key = fmt( 1, "zxf" );
            testStringIntern( key );
        };

        testString( r1 );
        testString( r2 );
    }

    static void testString( Runnable r ) throws InterruptedException {
        Thread t1 = new Thread( r, "test-1" );
        Thread t2 = new Thread( r, "test-2" );
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println( "------------------" );
    }

    private static void testString( String value ) {
        synchronized ( value ) {
            log.info( "{}-进入...", Thread.currentThread()
                    .getName() );
            SleepUtils.second( 1 );
            log.info( "{}-退出...", Thread.currentThread()
                    .getName() );
        }
    }

    private static void testStringIntern( String value ) {
        synchronized ( value.intern() ) {
            log.info( "{}-进入...", Thread.currentThread()
                    .getName() );
            SleepUtils.second( 1 );
            log.info( "{}-退出...", Thread.currentThread()
                    .getName() );
        }
    }

    static String fmt( int type, String name ) {
        return type + "-" + name;
    }

}
