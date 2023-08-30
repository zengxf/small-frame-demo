package test.thread.common_op;

import util.SleepUtils;

/**
 * suspend() 不会释放锁 <br>
 * suspend() 之后的线程状态还可能为 runable <br>
 * Eclipse 的 Debug 标签下的线程状态不对，没有及时更新
 * 
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestThreadSuspeed {

    static Object lock = new Object();

    public static void main( String[] args ) {
        // testNotUnlock();
        testStatus();
    }

    @SuppressWarnings( "removal" )
    static void testStatus() {
        Runnable r1 = () -> {
            int i = 0;
            while ( true ) {
                i = i++;
            }
        };

        Thread t1 = new Thread( r1, "test-01" );
        t1.start();
        System.out.println( "================= 0" );
        SleepUtils.second( 1 );
        t1.suspend();
        System.out.println( "================= 1" );
    }

    @SuppressWarnings( "removal" )
    static void testNotUnlock() {
        Runnable r1 = () -> {
            synchronized ( lock ) {
                System.out.println( Thread.currentThread()
                        .getName() + " ----------- 1" );
                SleepUtils.second( 1 );
                System.out.println( Thread.currentThread()
                        .getName() + " ----------- 2" );
                SleepUtils.second( 1 );
                System.out.println( Thread.currentThread()
                        .getName() + " ----------- 3" );
                SleepUtils.second( 1 );
                System.out.println( Thread.currentThread()
                        .getName() + " ----------- 4" );
            }
        };

        Thread t1 = new Thread( r1, "test-01" );
        t1.start();
        Thread t2 = new Thread( r1, "test-02" );
        t2.start();

        SleepUtils.second( 1 );
        System.out.println( "================= 0" );
        t2.suspend();
        System.out.println( "================= 1" );

        SleepUtils.second( 1 );
        System.out.println( "================= 2" );
        t2.resume();
        System.out.println( "================= 3" );
    }

}
