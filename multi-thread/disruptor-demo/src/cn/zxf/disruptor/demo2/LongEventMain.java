package cn.zxf.disruptor.demo2;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class LongEventMain {
    static AtomicInteger index = new AtomicInteger();

    @SuppressWarnings( "unchecked" )
    public static void main( String[] args ) throws Exception {
	// Executor that will be used to construct new threads for consumers
	ThreadFactory threadFactory = run -> {
	    Thread t = new Thread( run );
	    t.setName( "zxf-run-" + index.incrementAndGet() );
	    return t;
	};

	// The factory for the event
	LongEventFactory factory = new LongEventFactory();

	// Specify the size of the ring buffer, must be power of 2.
	int bufferSize = 1024;

	// Construct the Disruptor
	Disruptor<LongEvent> disruptor = new Disruptor<>( factory, bufferSize, threadFactory );

	// Connect the handler
	disruptor.handleEventsWith( new LongEventHandler() );

	// Start the Disruptor, starts all threads running
	disruptor.start();

	// Get the ring buffer from the Disruptor to be used for publishing.
	RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

	LongEventProducer producer = new LongEventProducer( ringBuffer );

	ByteBuffer bb = ByteBuffer.allocate( 8 );
	for ( long i = 0; true; i++ ) {
	    bb.putLong( 0, i * 10 );
	    producer.onData( bb );
	    Thread.sleep( 1000 );
	}
    }
}
