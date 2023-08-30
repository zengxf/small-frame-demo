package test.jdkapi.juc.structure;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 双端对列，只有一个锁，不管是操作头还是操作尾都是用同一个锁
 * <p>
 * 默认存的是尾，拿的是头
 * 
 * <p>
 * Created by zengxf on 2018-03-06
 */
public class TestLinkedBlockingDeque {

    public static void main( String[] args ) throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.put( 1 );
        deque.putFirst( 2 );
        deque.putLast( 3 );
        System.out.println( deque.take() );
        System.out.println( deque.takeFirst() );
        System.out.println( deque.takeLast() );
    }

}
