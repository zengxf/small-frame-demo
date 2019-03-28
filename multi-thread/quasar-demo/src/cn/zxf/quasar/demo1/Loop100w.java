package cn.zxf.quasar.demo1;

import java.util.concurrent.atomic.AtomicInteger;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.concurrent.CountDownLatch;

public class Loop100w {
    public static void main( String[] args ) throws InterruptedException {
	int FiberNumber = 1_000_000;
	CountDownLatch latch = new CountDownLatch( 1 );
	AtomicInteger counter = new AtomicInteger( 0 );

	for ( int i = 0; i < FiberNumber; i++ ) {
	    new Fiber<>( () -> {
		counter.incrementAndGet();
		if ( counter.get() == FiberNumber ) {
		    System.out.println( "done" );
		}
		Strand.sleep( 10000 );
	    } ).start();
	}
	latch.await();
    }
}
