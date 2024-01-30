package cn.zxf.reactor_demo.flux;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class TestFluxBuffer {

    public static void main(String[] args) {
//        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
//        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
//        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
        Flux.range(1, 10)
                .bufferTimeout(3, Duration.ofMillis(400))
                .log("xx")
                .subscribe(System.out::println);
    }

}
