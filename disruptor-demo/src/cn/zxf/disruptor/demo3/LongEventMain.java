package cn.zxf.disruptor.demo3;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongEventMain {
    static AtomicInteger index = new AtomicInteger();

    @SuppressWarnings( "unchecked" )
    public static void main( String[] args ) throws Exception {

        ThreadFactory threadFactory = run -> {
            Thread t = new Thread( run );
            t.setName( "zxf-run-" + index.incrementAndGet() );
            return t;
        };
        EventHandler<LongEvent> handler = ( event, sequence, endOfBatch ) -> {
            log.info( "Event: {}, sequence: {}, endOfBatch: {} ", event, sequence, endOfBatch );
            sleep( 1000 );
        };

        Disruptor<LongEvent> disruptor = new Disruptor<>( LongEvent::new, 8, threadFactory );
        disruptor.handleEventsWith( handler, handler, handler, handler, handler ); // 5 个消费者
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        for ( int i = 0; i < 2; i++ ) { // 2 个生产者
            int k = i;
            Thread t = new Thread( () -> {
                for ( long j = 0; true; j++ ) {
                    long sequence = ringBuffer.next(); // 会阻塞!!!
                    log.info( "pro seq: {}", sequence );

                    LongEvent e = ringBuffer.get( sequence );
                    e.set( k * 100_0000 + j * 10 );
                    ringBuffer.publish( sequence );

                    sleep( 100 );
                }
            } );
            t.setName( "pro-" + i );
            t.start();
        }

    }

    static void sleep( long m ) {
        try {
            Thread.sleep( m );
        } catch ( InterruptedException e1 ) {
            e1.printStackTrace();
        }
    }

}
