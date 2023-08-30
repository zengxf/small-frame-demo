package test.jdkapi.juc.thread_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import util.SleepUtils;

/**
 * <pre>
 * 1. shutdown() 和 shutdownNow() 后再提交走拒绝策略
 * 2. shutdown() 提交的线程会执行完
 * 3. shutdownNow() 等待的线程会移除，正在执行的线程会发出中断信号
 * running -> shutdown|stop -> tidying -> terminated
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-02-28
 */
public class TestShutdown {

    public static void main( String[] args ) {
        BlockingQueue<Runnable> q = new ArrayBlockingQueue<Runnable>( 1 );
        ThreadPoolExecutor p = new ThreadPoolExecutor( 2, 3, 0, TimeUnit.MILLISECONDS, q );
        IntStream.rangeClosed( 1, 4 ).forEach( i -> {
            p.execute( () -> {
                System.out.println( "" + i + "\t" + Thread.currentThread().getName() + " in ---" );
                SleepUtils.second( 1 );
                System.out.println( "" + i + "\t" + Thread.currentThread().getName() + " exit ---" );
            } );
        } );
        p.shutdown();
        // p.shutdownNow();
    }

}
