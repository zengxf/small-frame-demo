package test.new_features.jdk1_8.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class TestLongAdder extends Util {
    public static void main( String[] args ) {
	// LongAdder
	// 当多线程的更新比读取更频繁时，这个类通常比原子数值类性能更好
	// 缺点是较高的内存开销，因为它在内存中储存了一系列变量
	LongAdder adder = new LongAdder();
	ExecutorService executor = Executors.newFixedThreadPool( 2 );
	IntStream.range( 0, 1000 ).forEach( i -> executor.submit( adder::increment ) );
	stop( executor );
	System.out.println( adder.sumThenReset() ); // => 1000
	System.out.println( adder.sum() ); // => 0
    }
}
