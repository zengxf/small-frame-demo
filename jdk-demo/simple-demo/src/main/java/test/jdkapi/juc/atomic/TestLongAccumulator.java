package test.jdkapi.juc.atomic;

import java.util.concurrent.atomic.LongAccumulator;

/**
 * 累加器：分段累加操作，适用于高并发
 * 
 * <p>
 * Created by zengxf on 2018-03-06
 */
public class TestLongAccumulator {

    public static void main( String[] args ) {
        LongAccumulator acc = new LongAccumulator( ( i, j ) -> 5 + i + j, 0 );
        acc.accumulate( 2 );
        acc.accumulate( 2 );
        System.out.println( acc.get() );
    }

}
