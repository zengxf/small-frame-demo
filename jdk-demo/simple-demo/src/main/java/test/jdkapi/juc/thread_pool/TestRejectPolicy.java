package test.jdkapi.juc.thread_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import util.SleepUtils;

/**
 * <pre>
 * 拒绝策略默认 4 种：
 * 1. AbortPolicy 异常中止
 * 2. DiscardPolicy 直接丢弃
 * 3. CallerRunsPolicy 直接由提交任务的线程执行
 * 4. DiscardOldestPolicy 丢弃等待队列最旧的，然后再提交一次(新加入队列或执行)
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-02-28
 */
public class TestRejectPolicy {

    public static void main( String[] args ) {
        RejectedExecutionHandler rej = new DiscardOldestPolicy(); // 丢弃了 3

        BlockingQueue<Runnable> q = new ArrayBlockingQueue<Runnable>( 2 );
        ThreadPoolExecutor p = new ThreadPoolExecutor( 2, 3, 0, TimeUnit.MILLISECONDS, q, rej );
        IntStream.rangeClosed( 1, 6 ).forEach( i -> {
            p.execute( () -> {
                System.out.println( "" + i + "\t" + Thread.currentThread().getName() + " in ---" );
                SleepUtils.second( 1 );
                System.out.println( "" + i + "\t" + Thread.currentThread().getName() + " exit ---" );
            } );
        } );

        p.shutdown();
    }

}
