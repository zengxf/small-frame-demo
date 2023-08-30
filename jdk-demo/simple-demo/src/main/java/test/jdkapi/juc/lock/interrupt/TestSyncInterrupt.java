package test.jdkapi.juc.lock.interrupt;

import util.SleepUtils;

public class TestSyncInterrupt {

    public static void main( String[] args ) {
        Object lock = new Object();
        Runnable r = () -> {
            synchronized ( lock ) {
                System.out.println( Thread.currentThread()
                        .getName() + " in ..." );
                SleepUtils.second( 2 );
                System.out.println( Thread.currentThread()
                        .getName() + " out ..." );
            }
        };
        Thread t1 = new Thread( r, "test-1" );
        Thread t2 = new Thread( r, "test-2" );

        t1.start();
        SleepUtils.millisecond( 100 );
        t2.start();
        System.out.println( "t2 start" );

        SleepUtils.millisecond( 100 );
        System.out.println( "t2 interrupt" );
        t2.interrupt();
    }

}
