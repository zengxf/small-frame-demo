package cn.zxf.reactor_demo.flux;

import reactor.core.publisher.Flux;

public class TestFluxWindow {

    public static void main(String[] args) {
        Flux.range(1, 100)
                .window(20)
                .subscribe(flux -> {
                    flux.buffer(5)
                            .subscribe(list -> System.out.println(list + "====" + Thread.currentThread()));
                    System.out.println("======================" + Thread.currentThread());
//           System.out.println( flux.toStream().collect( Collectors.toList() ));
                });
//        Flux.range(1, 100).window(20).take(2).toStream().forEach(System.out::println);
    }

}
