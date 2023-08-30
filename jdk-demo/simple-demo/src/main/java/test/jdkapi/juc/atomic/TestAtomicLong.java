package test.jdkapi.juc.atomic;

import java.util.concurrent.atomic.AtomicLong;

public class TestAtomicLong {

    public static void main( String[] args ) {
        AtomicLong num = new AtomicLong();
        num.incrementAndGet();
        System.out.println( num.get() );
    }

}
