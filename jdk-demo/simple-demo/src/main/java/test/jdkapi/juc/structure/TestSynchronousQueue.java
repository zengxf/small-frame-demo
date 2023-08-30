package test.jdkapi.juc.structure;

import java.util.concurrent.SynchronousQueue;

import util.SleepUtils;

/**
 * 公平方式。要达到等待的效果用 put() 和 take() <br>
 * [源码分析](http://cmsblogs.com/?p=2418")
 * 
 * <p>
 * Created by zengxf on 2018-03-01
 */
public class TestSynchronousQueue {

    public static void main( String[] args ) throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        Runnable r = () -> {
            System.out.println( Thread.currentThread().getName() + "\t in..." );
            SleepUtils.second( 1 );
            try {
                System.out.println( Thread.currentThread().getName() + "\t get: " + queue.take() );
            } catch ( InterruptedException e ) {
            }
            System.out.println( Thread.currentThread().getName() + "\t done" );
        };
        new Thread( r, "test-read-01" ).start();
        SleepUtils.second( 2 );
        queue.put( 1 );
        System.out.println( "main done" );
    }

}
