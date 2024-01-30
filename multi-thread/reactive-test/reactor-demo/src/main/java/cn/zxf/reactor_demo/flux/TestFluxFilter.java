package cn.zxf.reactor_demo.flux;

import reactor.core.publisher.Flux;

public class TestFluxFilter {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .log()
                .filter(i -> i % 2 == 0)
                .log()
                .subscribe(System.out::println);
    }

}
