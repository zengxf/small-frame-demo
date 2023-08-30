package test.thread;

import java.util.concurrent.SynchronousQueue;

public class TestSynchronousQueue {
    public static void main( String[] args ) throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        new Thread( () -> {
            System.out.println( "## - 1 - " + queue.poll() );
            try {
                Thread.sleep( 100L );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            try {
                System.out.println( "## - 2 - " + queue.take() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } ).start();
        queue.put( "d" );
        System.out.println( "-- 0" );
        // queue.put( "e" );
        // System.out.println( "-- 1" );
    }
}
