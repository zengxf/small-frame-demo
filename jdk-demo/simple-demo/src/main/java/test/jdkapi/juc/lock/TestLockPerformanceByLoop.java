package test.jdkapi.juc.lock;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestLockPerformanceByLoop {

    static ReentrantLock look         = new ReentrantLock( false );
    static int           thread_count = 1;
    static int           times        = 100_0000;
    static Object        lkObj        = new Object();

    public static void main( String[] args ) {
        for ( int i = 0; i < 10_0000; i++ ) {
            inLook1();
            inSync1();
        }
        testLook();
        testSync();
    }

    static void testLook() {
        Runnable r = () -> inLook();

        List<Thread> threads = IntStream.rangeClosed( 1, thread_count )
                .boxed()
                .map( i -> new Thread( r, "look-t-" + i ) )
                .collect( Collectors.toList() );

        long start = System.currentTimeMillis();

        threads.forEach( Thread::start );

        threads.forEach( t -> {
            try {
                t.join();
            } catch ( InterruptedException e ) {
            }
        } );

        System.out.println( "look - " + ( System.currentTimeMillis() - start ) );
    }

    static void inLook() {
        look.lock();
        try {
            for ( int i = 0; i < times; i++ ) {
                inLook1();
            }
        } finally {
            look.unlock();
        }
    }

    static void inLook1() {
        look.lock();
        try {
        } finally {
            look.unlock();
        }
    }

    static void testSync() {
        Runnable r = () -> inSync();

        List<Thread> threads = IntStream.rangeClosed( 1, thread_count )
                .boxed()
                .map( i -> new Thread( r, "sync-t-" + i ) )
                .collect( Collectors.toList() );

        long start = System.currentTimeMillis();

        threads.forEach( Thread::start );

        threads.forEach( t -> {
            try {
                t.join();
            } catch ( InterruptedException e ) {
            }
        } );

        System.out.println( "sync - " + ( System.currentTimeMillis() - start ) );
    }

    static void inSync() {
        synchronized ( lkObj ) {
            for ( int i = 0; i < times; i++ ) {
                inSync1();
            }
        }
    }

    static void inSync1() {
        synchronized ( lkObj ) {
        }
    }

}
