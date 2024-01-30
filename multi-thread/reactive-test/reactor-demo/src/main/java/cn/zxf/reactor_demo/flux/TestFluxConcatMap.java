package cn.zxf.reactor_demo.flux;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class TestFluxConcatMap {

    public static void main(String[] args) {
        Flux.just(5, 10)
                .concatMap(x -> intervalMillis(x * 10, 100).take(x))
                .toStream()
                .forEach(System.out::println);
    }

    static Flux<Long> intervalMillis(int delay, int period) {
        return Flux.interval(Duration.ofMillis(delay), Duration.ofMillis(period));
    }

}
