package cn.zxf.reactor_demo.flux;

import reactor.core.publisher.Flux;

public class TestFluxFilter {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .filter(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }

}
