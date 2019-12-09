package cn.zxf.reactor_demo.flux;

import java.time.Duration;
import java.util.Arrays;

import reactor.core.publisher.Flux;

public class TestFluxCombineLatest {

    public static void main( String[] args ) {
        Flux.combineLatest(
                Arrays::toString,
                intervalMillis(100, 5).take(5),
                intervalMillis(50, 100).take(5)
        ).toStream().forEach(System.out::println);
    }

    static Flux<Long> intervalMillis( int delay, int period ) {
        return Flux.interval( Duration.ofMillis( delay ), Duration.ofMillis( period ) );
    }

}
