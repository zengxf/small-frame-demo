package test.jdkapi.juc.structure;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TestConcurrentLinkedQueue {

    public static void main( String[] args ) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.offer( "test" );
        queue.poll();
    }

}
