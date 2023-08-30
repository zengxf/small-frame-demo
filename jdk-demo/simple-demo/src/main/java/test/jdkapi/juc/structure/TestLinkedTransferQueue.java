package test.jdkapi.juc.structure;

import java.util.concurrent.LinkedTransferQueue;

import util.SleepUtils;

/**
 * 通过 transfer() 方法直到有其他线程拿取 <br>
 * [源码分析](http://cmsblogs.com/?p=2433)
 * 
 * <p>
 * Created by zengxf on 2018-03-01
 */
public class TestLinkedTransferQueue {

    public static void main( String[] args ) throws InterruptedException {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();

        Runnable r = () -> {
            System.out.println( Thread.currentThread().getName() + "\t in..." );
            SleepUtils.second( 1 );
            try {
                System.out.println( Thread.currentThread().getName() + "\t get: " + queue.take() );
                SleepUtils.second( 1 );
                System.out.println( Thread.currentThread().getName() + "\t get: " + queue.take() );
            } catch ( InterruptedException e ) {
            }
            System.out.println( Thread.currentThread().getName() + "\t done" );
        };
        new Thread( r, "test-read-01" ).start();

        queue.transfer( 1 );
        System.out.println( "main transfer 1 done" );
        queue.transfer( 2 );
        System.out.println( "main done" );
    }

}
