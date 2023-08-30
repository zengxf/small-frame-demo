package test.thread.dead_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TestResolveDeadLock {
    static ReentrantLock lock1 = new ReentrantLock(), lock2 = new ReentrantLock();

    public static void main( String[] args ) {
        Runnable r1 = () -> {
            try {
                boolean sign = lock1.tryLock( 100, TimeUnit.MILLISECONDS );
                if ( sign ) {
                    try {
                        sleep( 100 );
                        System.out.println( "1-1 done" );
                        sign = lock2.tryLock( 100, TimeUnit.MILLISECONDS );
                        if ( sign ) {
                            try {
                                sleep( 100 );
                                System.out.println( "1-2 done" );
                            } finally {
                                lock2.unlock();
                            }
                        } else {
                            System.out.println( "1-did not get the lock-lock2" );
                        }
                    } finally {
                        lock1.unlock();
                    }
                }
            } catch ( InterruptedException e ) {
            }
        };

        Runnable r2 = () -> {
            try {
                boolean sign = lock2.tryLock( 100, TimeUnit.MILLISECONDS );
                if ( sign ) {
                    try {
                        sleep( 100 );
                        System.out.println( "2-1 done" );
                        sign = lock1.tryLock( 100, TimeUnit.MILLISECONDS );
                        if ( sign ) {
                            try {
                                sleep( 100 );
                                System.out.println( "2-2 done" );
                            } finally {
                                lock1.unlock();
                            }
                        } else {
                            System.out.println( "2-did not get the lock-lock1" );
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
            } catch ( InterruptedException e ) {
            }
        };
        new Thread( r1, "t-1" ).start();
        new Thread( r2, "t-2" ).start();
    }

    static void sleep( int millis ) {
        try {
            Thread.sleep( millis );
        } catch ( InterruptedException e ) {
        }
    }

}
