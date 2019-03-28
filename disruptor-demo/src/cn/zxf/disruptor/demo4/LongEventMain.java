package cn.zxf.disruptor.demo4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;

import cn.zxf.disruptor.demo3.LongEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongEventMain {

    public static void main( String[] args ) {
        WorkHandler<LongEvent> handler = ( event ) -> {
            log.info( "Event: {}", event );
            sleep( 100 );
        };

        RingBuffer<LongEvent> ringBuffer = RingBuffer.createMultiProducer( LongEvent::new, 8, new BlockingWaitStrategy() );
        @SuppressWarnings( "unchecked" )
        WorkerPool<LongEvent> wp = new WorkerPool<>( ringBuffer, ringBuffer.newBarrier(), new EventExceptionHandler() //
                , handler, handler, handler, handler, handler );
        ExecutorService executor = Executors.newCachedThreadPool();
        wp.start( executor );

        for ( int i = 0; i < 10; i++ ) {
            int k = i;
            Thread t = new Thread( () -> {
                for ( long j = 0; j < 10; j++ ) {
                    long sequence = ringBuffer.next(); // 会阻塞!!!
                    log.info( "pro seq: {}", sequence );

                    LongEvent e = ringBuffer.get( sequence );
                    e.set( k * 100_0000 + j * 10 );
                    ringBuffer.publish( sequence );

                    sleep( 1000 );
                }
            } );
            t.setName( "pro-" + i );
            t.start();
        }

        executor.shutdown();
        // wp.halt();
        wp.drainAndHalt();
    }

    static void sleep( long m ) {
        try {
            Thread.sleep( m );
        } catch ( InterruptedException e1 ) {
            e1.printStackTrace();
        }
    }

}

class EventExceptionHandler implements ExceptionHandler<LongEvent> {
    public void handleEventException( Throwable ex, long sequence, LongEvent event ) {
    }

    public void handleOnStartException( Throwable ex ) {
    }

    public void handleOnShutdownException( Throwable ex ) {
    }
}