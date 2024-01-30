package cn.zxf.reactor_demo.flux;

import reactor.core.publisher.Flux;

public class TestFluxTake {

    public static void main(String[] args) {
//        Flux.range(1, 1000).take(10).subscribe(System.out::println);
//        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        Flux.range(1, 1000)
                .takeWhile(i -> i < 10)
                .subscribe(System.out::println);
//        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
//        Flux.range(1, 1000).takeUntilOther(Flux.never()).subscribe(System.out::println);
    }

}
