package test.new_features.jdk1_8.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


/**
 * 信号量-测试
 * 
 * <p>
 * Created by zxf on 2017-07-18
 */
public class TestSemaphore extends Util {

    public static void main( String[] args ) {
	ExecutorService executor = Executors.newFixedThreadPool( 10 );
	Semaphore semaphore = new Semaphore( 5 );
	Runnable longRunningTask = () -> {
	    boolean permit = false;
	    try {
		permit = semaphore.tryAcquire( 2, TimeUnit.SECONDS );
		if ( permit ) {
		    System.out.println( "Semaphore acquired" );
		    sleep( 1 );
		} else {
		    System.out.println( "Could not acquire semaphore" );
		}
	    } catch ( InterruptedException e ) {
		throw new IllegalStateException( e );
	    } finally {
		if ( permit ) {
		    semaphore.release();
		}
	    }
	};
	IntStream.range( 0, 8 ).forEach( i -> executor.submit( longRunningTask ) );
	stop( executor );
    }

}
