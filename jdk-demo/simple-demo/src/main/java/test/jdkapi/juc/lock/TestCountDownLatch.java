package test.jdkapi.juc.lock;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * await() 可以多个线程等待
 * 
 * <p>
 * Created by zengxf on 2018-02-22
 */
public class TestCountDownLatch {

    public static void main( String[] args ) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch( 2 );

        IntStream.of( 1, 2 ).forEach( i -> {
            new Thread() {
                @Override
                public void run() {
                    System.out.println( "await thread " + i + " exec ..." );
                    try {
                        latch.await();
                    } catch ( InterruptedException e ) {
                    }
                    System.out.println( "await thread " + i + " done ..." );
                }
            }.start();
        } );

        IntStream.of( 1, 2 ).forEach( i -> {
            new Thread() {
                @Override
                public void run() {
                    System.out.println( "countDown thread " + i + " exec ..." );
                    latch.countDown();
                }
            }.start();
        } );

        latch.await();
        System.out.println( "main done 1" );

        latch.await();
        System.out.println( "main done 2" );
    }

}
