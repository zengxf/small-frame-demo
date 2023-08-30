package test.jdkapi.juc.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * 都是 await() 方法里处理暂停和唤醒
 * 
 * <p>
 * Created by zengxf on 2018-02-22
 */
public class TestCyclicBarrier {

    public static void main( String[] args ) {
        int count = 2;

        CyclicBarrier cyclicBarrier = new CyclicBarrier( count, () -> {
            System.out.println( " ---------- " );
        } );

        Runnable r = () -> {
            try {
                System.out.println( Thread.currentThread().getName() + " 到了 1" );
                cyclicBarrier.await();

                System.out.println( Thread.currentThread().getName() + " 到了 2" );
                cyclicBarrier.await();

                System.out.println( Thread.currentThread().getName() + " 到了 3" );
                cyclicBarrier.await();
            } catch ( Exception e ) {
            }
        };

        IntStream.rangeClosed( 1, count * 2 ).forEach( i -> {
            new Thread( r, "test-" + i ).start();
        } );
    }

}
