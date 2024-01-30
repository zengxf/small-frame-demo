package cn.zxf.reactor_demo.flux;

import reactor.core.publisher.Flux;

public class TestFlux {

    public static void main(String[] args) throws InterruptedException {
        Flux.create(sink -> {
                    for (int i = 0; i < 10; i++) {
                        sink.next(i);
                    }
                    sink.complete();
                })
                .subscribe(System.out::println);
    }

}
