package test.new_features.jdk1_8.stream;

import java.util.concurrent.ForkJoinPool;

/**
 * list.parallelStream()
 * <p>
 * 需要避免实现又慢又卡的流式操作，因为它可能 会拖慢你应用中严重依赖并行流的其它部分。
 * 
 * <p>
 * Created by zxf on 2017-04-24
 */
public class TestForkJoinPool {

    public static void main( String[] args ) throws Throwable {
        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=5
        System.setProperty( "java.util.concurrent.ForkJoinPool.common.parallelism", "5" );
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println( commonPool );
        System.out.println( commonPool.getParallelism() ); //
    }
}
