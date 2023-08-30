package test.jdkapi.juc.thread_pool;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import util.SleepUtils;

import static java.util.concurrent.Executors.*;

/**
 * 如果没有处理好异常，则当前的任务以后是不会执行的 <br>
 * 即：任务之间不会有影响；但同一任务未处理异常时，将不会再继续执行 <br>
 * FixedRate() 从任务开始计时 <br>
 * FixedDelay() 从任务结束后才计时 <br>
 * 
 * <p>
 * Created by zengxf on 2018-03-06
 */
public class TestSchedule {

    public static void main( String[] args ) {
        // testFixedRate();
        testError();
    }

    static void testFixedRate() {
        ScheduledExecutorService pool = newScheduledThreadPool( 4 );
        Runnable r = () -> {
            System.out.println( "--------" );
            SleepUtils.second( 2 );
            System.out.printf( "--- ok --- %tT %n", System.currentTimeMillis() );
        };
        System.out.printf( "--- start --- %tT %n", System.currentTimeMillis() );
        pool.scheduleAtFixedRate( r, 1000, 1000, TimeUnit.MILLISECONDS );
    }

    static void testFixedDelay() {
        ScheduledExecutorService pool = newScheduledThreadPool( 4 );
        Runnable r = () -> {
            System.out.println( "--------" );
            SleepUtils.second( 2 );
            System.out.printf( "--- ok --- %tT %n", System.currentTimeMillis() );
        };
        System.out.printf( "--- start --- %tT %n", System.currentTimeMillis() );
        pool.scheduleWithFixedDelay( r, 1000, 1000, TimeUnit.MILLISECONDS );
    }

    static void testError() {
        ScheduledExecutorService pool = newScheduledThreadPool( 1 );
        Runnable r = () -> {
            System.out.println( "r1 -------- " + Thread.currentThread()
                    .getName() );
            if ( System.currentTimeMillis() % 2 == 0 ) {
                System.out.println( "--- error" );
                System.out.println( 1 / 0 );
            } else {
                System.out.println( "--- ok" );
            }
        };
        Runnable r2 = () -> {
            System.out.println( "r2 -------- " + Thread.currentThread()
                    .getName() );
            try {
                Thread.sleep( 1100 );
            } catch ( InterruptedException e ) {
            }
        };
        pool.scheduleAtFixedRate( r, 1000, 1000, TimeUnit.MILLISECONDS );
        pool.scheduleAtFixedRate( r2, 1000, 1000, TimeUnit.MILLISECONDS );
    }

}
