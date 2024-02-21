package cn.zxf.reactor_demo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class TestScheduler {

    public static void main(String[] args) {
        // 输出：[[boundedElastic-1]] <[single-1]> {parallel-1}
        Flux.create(sink -> {
                    sink.next("{" + Thread.currentThread().getName() + "--1}");
                    sink.next("{" + Thread.currentThread().getName() + "--2}");
                    sink.complete();
                })
                .subscribeOn(Schedulers.parallel())     // 上面的 sink -> next 用的线程
                .publishOn(Schedulers.single())         // 下面的 map 用的线程
                .map(x -> String.format("<[%s]> %s", Thread.currentThread().getName(), x))
                // .log()
                .publishOn(Schedulers.boundedElastic()) // 下面的 map 用的线程
                .map(x -> String.format("[[%s]] %s", Thread.currentThread().getName(), x))
                // .log()
                .toStream()
                .forEach(v -> log.info("v -> {}", v));
    }

}
