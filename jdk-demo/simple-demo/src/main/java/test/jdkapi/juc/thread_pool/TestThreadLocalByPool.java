package test.jdkapi.juc.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadLocalByPool {

    public static void main( String[] args ) {
        AtomicInteger sign = new AtomicInteger( 0 );
        ThreadLocal<String> tl = ThreadLocal.withInitial( () -> "test-" + sign.incrementAndGet() );
        ExecutorService ex = Executors.newFixedThreadPool( 1 );

        ex.execute( () -> {
            System.out.println( tl.get() );
            tl.remove();
        } );
        ex.execute( () -> {
            System.out.println( tl.get() );
            tl.set( "test-10" );
        } );
        ex.execute( () -> {
            System.out.println( tl.get() );
        } );
        
        ex.shutdown();
    }

}
