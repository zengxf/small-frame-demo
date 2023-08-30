package test.new_features.jdk1_8.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Util {

    public static void sleep( int i ) {
	try {
	    Thread.sleep( i * 1000L );
	} catch ( InterruptedException e ) {
	    e.printStackTrace();
	}
    }

    public static void stop( ExecutorService executor ) {
	try {
	    executor.shutdown();
	    executor.awaitTermination( 60, TimeUnit.SECONDS );
	} catch ( InterruptedException e ) {
	    System.err.println( "termination interrupted" );
	} finally {
	    if ( !executor.isTerminated() ) {
		System.err.println( "killing non-finished tasks" );
	    }
	    executor.shutdownNow();
	}
    }

}
