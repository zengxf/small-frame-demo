package cn.zxf.reactor_demo.mono;

import reactor.core.publisher.Mono;

public class TestMonoSwitchIfEmpty {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--");
        Mono.justOrEmpty(null)
                .switchIfEmpty(Mono.error(new Exception("null-error")))
                .subscribe(System.out::println);
    }

}
