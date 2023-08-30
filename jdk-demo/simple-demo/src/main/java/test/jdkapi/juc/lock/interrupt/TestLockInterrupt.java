package test.jdkapi.juc.lock.interrupt;

import java.util.concurrent.locks.ReentrantLock;

import util.SleepUtils;

public class TestLockInterrupt {

    public static void main( String[] args ) {
        // testByLock();
        testBySync();
    }

    static void testBySync() {
        Runnable r = () -> {
            Thread currentThread = Thread.currentThread();
            System.out.println( currentThread.getName() + " sync ..." );
            synchronized ( "a" ) {
                System.out.println( currentThread.getName() + " in ..." );
                System.out.println( currentThread.getName() + " status: " + currentThread.isInterrupted() );
                SleepUtils.second( 2 );
                System.out.println( currentThread.getName() + " out ..." );
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

    static void testByLock() {
        ReentrantLock lock = new ReentrantLock();
        Runnable r = () -> {
            try {
                lock.lockInterruptibly();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
                return;
            }
            try {
                Thread currentThread = Thread.currentThread();
                System.out.println( currentThread.getName() + " in ..." );
                SleepUtils.second( 2 );
                System.out.println( currentThread.getName() + " out ..." );
            } finally {
                lock.unlock();
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
