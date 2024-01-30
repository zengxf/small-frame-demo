package cn.zxf.reactor_demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class TestScheduler {

    public static void main(String[] args) {
        // [[elastic-2]] <[single-1]> {parallel-1}
        Flux.create(sink -> {
                    sink.next("{" + Thread.currentThread().getName() + "}");
                    sink.complete();
                })
                .subscribeOn(Schedulers.parallel())     // 上面的 sink -> next 用的线程
                .publishOn(Schedulers.single())         // 下面的 map 用的线程
                .map(x -> String.format("<[%s]> %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.boundedElastic()) // 下面的 map 用的线程
                .map(x -> String.format("[[%s]] %s", Thread.currentThread().getName(), x))
                .toStream()
                .forEach(System.out::println);
    }

}
