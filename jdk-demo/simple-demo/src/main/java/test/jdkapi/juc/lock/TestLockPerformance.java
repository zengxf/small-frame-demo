package test.jdkapi.juc.lock;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestLockPerformance {

    static ReentrantLock look         = new ReentrantLock( false );
    static int           thread_count = 13;
    static int           times        = 100_0000;
    static Object        lkObj        = new Object();

    public static void main( String[] args ) {
        for ( int i = 0; i < 10_0000; i++ ) {
            inLook();
            inSync();
        }
        testLook();
        testSync();
    }

    static void testLook() {
        Runnable r = () -> loopLook();

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

    static void loopLook() {
        for ( int i = 0; i < times; i++ ) {
            inLook();
        }
    }

    static void inLook() {
        look.lock();
        try {
            inLook1();
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
        Runnable r = () -> loopSync();

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

    static void loopSync() {
        for ( int i = 0; i < times; i++ ) {
            inSync();
        }
    }

    static void inSync() {
        synchronized ( lkObj ) {
            inSync1();
        }
    }

    static void inSync1() {
        synchronized ( lkObj ) {
        }
    }

}
