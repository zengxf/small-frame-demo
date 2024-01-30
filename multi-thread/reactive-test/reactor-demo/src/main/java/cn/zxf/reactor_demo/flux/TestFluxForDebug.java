package cn.zxf.reactor_demo.flux;

import reactor.core.publisher.Flux;

public class TestFluxForDebug {

    public static void main(String[] args) throws InterruptedException {
        Flux.just(1, 0)
                .map(x -> 1 / (x - 1))
                .map(x -> 1 / x)
                .checkpoint("test", true)
                .subscribe(System.out::println);

    }

}
