package test.jdkapi.juc.structure;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestArrayBlockingQueue {

    public static void main( String[] args ) throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>( 20 );

        // 特殊值
        queue.offer( 1 );
        queue.poll();
        queue.peek();

        // 异常
        queue.add( 2 );
        queue.remove();
        queue.element();

        // 阻塞
        queue.put( 10 );
        queue.take();

        // 超时
        queue.offer( 3, 1, TimeUnit.SECONDS );
        queue.poll( 1, TimeUnit.SECONDS );
    }

}
