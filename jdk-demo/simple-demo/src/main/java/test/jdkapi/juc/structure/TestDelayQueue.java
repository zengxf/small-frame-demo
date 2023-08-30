package test.jdkapi.juc.structure;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelayQueue {
    public static void main( String[] args ) throws InterruptedException {
        DelayQueue<MyDelayed> queue = new DelayQueue<>();
        new Thread( () -> {
            System.out.println( "## - 1 poll - " + queue.poll() );
            try {
                System.out.println( "## - 2 - start " );
                System.out.println( "## - 2 take - " + queue.take() );
                System.out.println( "## 0 == " + System.currentTimeMillis() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        } ).start();
        queue.put( new MyDelayed( "d" ) );
        System.out.println( "-- 0 == " + System.currentTimeMillis() );
        // queue.put( "e" );
        // System.out.println( "-- 1" );

    }

    // 默认等 1s
    static class MyDelayed implements Delayed {

        String sign;
        long   time = 0;

        public MyDelayed( String sign ) {
            super();
            this.sign = sign;
            time = TimeUnit.SECONDS.toNanos( 1 ) + System.nanoTime();
            System.out.println( "queue - time: " + time );
        }

        @Override
        public int compareTo( Delayed o ) {
            return 0;
        }

        @Override
        public long getDelay( TimeUnit unit ) { // DelayQueue 用的是 NANOSECONDS
            long convert = unit.convert( time - System.nanoTime(), TimeUnit.NANOSECONDS );
            // System.out.println( "unit: " + unit );
            System.out.println( "delay - nano: " + convert );
            return convert;
        }

    }
}
