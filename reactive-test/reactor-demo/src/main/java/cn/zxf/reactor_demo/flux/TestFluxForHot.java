package cn.zxf.reactor_demo.flux;

import java.time.Duration;

import reactor.core.publisher.Flux;

/*** “热”序列 */
public class TestFluxForHot {

    public static void main( String[] args ) throws InterruptedException {
        Flux<Long> source = intervalMillis( 1000, 1000 ).take( 10 )
                .publish()
                .autoConnect();
        source.subscribe();
        Thread.sleep( 5000 );
        source.toStream()
                .forEach( System.out::println );
    }

    static Flux<Long> intervalMillis( int delay, int period ) {
        return Flux.interval( Duration.ofMillis( delay ), Duration.ofMillis( period ) );
    }

}
