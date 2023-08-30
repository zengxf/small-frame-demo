package test.jdkapi.juc.structure;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 元素类必须实现 Comparable 接口，或提供 Comparator 对象 <br>
 * 默认顺序排序
 * 
 * <p>
 * Created by zengxf on 2018-03-02
 */
public class TestPriorityBlockingQueue {

    public static void main( String[] args ) throws InterruptedException {
        testCommon();
        // testError();
    }

    static void testCommon() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.put( 20 );
        queue.put( 10 );
        queue.put( 30 );
        System.out.println( queue.take() );
        System.out.println( queue.take() );
        System.out.println( queue.take() );
    }

    static void testError() throws InterruptedException {
        PriorityBlockingQueue<TestPriorityBlockingQueueInner> queue = new PriorityBlockingQueue<>();
        queue.put( new TestPriorityBlockingQueueInner() );
        System.out.println( queue.take() );
    }

    static class TestPriorityBlockingQueueInner {
    }

}
