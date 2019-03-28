package cn.zxf.disruptor.demo;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WorkerPool;

public class DisruptorTest {

    public static void main( String[] args ) throws InterruptedException {
        // 创建一个RingBuffer对象
        RingBuffer<IntEvent> ringBuffer = RingBuffer.createMultiProducer( IntEvent.INT_ENEVT_FACTORY, //
                16, new SleepingWaitStrategy() );

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        IntEventProducer[] producers = new IntEventProducer[1];
        for ( int i = 0; i < producers.length; i++ ) {
            producers[i] = new IntEventProducer();
        }
        WorkerPool<IntEvent> crawler = new WorkerPool<IntEvent>( ringBuffer, sequenceBarrier, new IntEventExceptionHandler(), producers );
        SequenceBarrier sb = ringBuffer.newBarrier( crawler.getWorkerSequences() );
        IntEventProcessor[] processors = new IntEventProcessor[1];
        for ( int i = 0; i < processors.length; i++ ) {
            processors[i] = new IntEventProcessor();
        }

        WorkerPool<IntEvent> applier = new WorkerPool<IntEvent>( ringBuffer, sb, new IntEventExceptionHandler(), processors );
        ThreadPoolExecutor executor = new ThreadPoolExecutor( 7, 7, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>( 5 ) );
        crawler.start( executor );
        applier.start( executor );

        while ( true ) {
            Thread.sleep( 1000 );
            long lastSeq = ringBuffer.next();
            ringBuffer.publish( lastSeq );
        }
    }
}

class IntEventExceptionHandler implements ExceptionHandler<IntEvent> {
    public void handleEventException( Throwable ex, long sequence, IntEvent event ) {
    }

    public void handleOnStartException( Throwable ex ) {
    }

    public void handleOnShutdownException( Throwable ex ) {
    }
}