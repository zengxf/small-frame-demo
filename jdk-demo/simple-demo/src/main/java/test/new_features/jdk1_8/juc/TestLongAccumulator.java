package test.new_features.jdk1_8.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

public class TestLongAccumulator extends Util {

    public static void main( String[] args ) {
	test1();
	System.out.println( "--" );
	test2();
    }

    static void test2() {
	LongBinaryOperator op = Long::sum;
	LongAccumulator accumulator = new LongAccumulator( op, 1L );
	accumulator.accumulate( 10 );
	accumulator.accumulate( 10 );
	System.out.println( accumulator.get() ); // 21
	System.out.println( accumulator.getThenReset() ); // 21
    }

    static void test1() {
	LongBinaryOperator op = ( v, x ) -> 2 * v + x; // x 是参数
	LongAccumulator accumulator = new LongAccumulator( op, 1L ); // base: 1
	ExecutorService executor = Executors.newFixedThreadPool( 4 );
	int end = 4;
	IntStream.range( 0, end ).forEach( i -> executor.submit( () -> accumulator.accumulate( i ) ) );
	stop( executor );
	System.out.println( accumulator.getThenReset() ); // => 38
	IntStream.range( 0, end ).forEach( System.out::println ); // 0 1 2 3
    }

}
