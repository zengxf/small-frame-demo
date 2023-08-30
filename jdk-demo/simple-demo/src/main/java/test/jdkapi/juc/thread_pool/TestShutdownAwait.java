package test.jdkapi.juc.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

import util.SleepUtils;

public class TestShutdownAwait {

    public static void main( String[] args ) throws InterruptedException {
        ExecutorService service = Executors.newWorkStealingPool( 2 );
        LongAdder sum = new LongAdder();
        long start = System.currentTimeMillis();
        Stream.of( 2, 3, 4, 5, 6 )
                .<Runnable> map( f -> () -> {
                    String src = f.toString();
                    System.out.println( "str -> " + src );
                    SleepUtils.millisecond( 1000 );
                    sum.increment();
                } )
                .forEach( service::submit );

        service.shutdown();

        boolean sign = false;
        while ( !( sign = service.awaitTermination( 1000, TimeUnit.MILLISECONDS ) ) ) {
            System.out.println( sign );
        }

        long uses = System.currentTimeMillis() - start;
        System.out.printf( "提取图片完成（共 %d 张图片）！用时：%s ms %n", sum.longValue(), uses );
    }

}
