package cn.zxf.reactor_demo.flux;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class TestFluxMerge {

    public static void main( String[] args ) {
        Flux.merge(intervalMillis(0, 100).take(5), intervalMillis(50, 100).take(5))
                .toStream()
                .forEach(System.out::println);
        Flux.mergeSequential(intervalMillis(0, 100).take(5), intervalMillis(50, 100).take(5))
                .toStream()
                .forEach(System.out::println);
    }

    static Flux<Long> intervalMillis( int delay, int period ) {
        return Flux.interval( Duration.ofMillis( delay ), Duration.ofMillis( period ) );
    }
    
}
