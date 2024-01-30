package cn.zxf.reactor_demo.flux;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class TestFluxHotCold {

    public static void main(String[] args) throws InterruptedException {
        final Flux<Long> source = intervalMillis(0, 1000)
                .take(10)
                .publish()      // 转换成 ConnectableFlux 对象
                .autoConnect(); // 有订阅者时才开始产生消息
        source.subscribe();     // 订阅

        Thread.sleep(5000);
        source.toStream() // 第二个订阅者
                .forEach(System.out::println);
    }

    static Flux<Long> intervalMillis(int delay, int period) {
        return Flux.interval(Duration.ofMillis(delay), Duration.ofMillis(period));
    }

}
