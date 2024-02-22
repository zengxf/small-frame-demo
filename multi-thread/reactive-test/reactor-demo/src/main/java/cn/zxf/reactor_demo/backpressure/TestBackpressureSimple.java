package cn.zxf.reactor_demo.backpressure;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

public class TestBackpressureSimple {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("reactor.bufferSize.small", "5"); // 缓存大小设置为 5
        System.out.println(Queues.SMALL_BUFFER_SIZE); // 最终为 16

        Flux.create(sink -> { // 内部对列没有阻塞操作，会对数组进行扩容，所以有 OOM 的风险
                    for (int i = 0; i < 20; i++) {
                        String v = String.format("<[%s]> %s", Thread.currentThread().getName(), i);
                        sink.next(v);
                        System.out.println("create v: " + v);
                    }
                    sink.complete();
                })
                .subscribeOn(Schedulers.parallel()) // 上面的 sink -> next 用的线程
                .publishOn(Schedulers.single()) // 下面操作符用的线程
                .subscribe(v -> {
                    System.out.println(Thread.currentThread().getName() + " == v: " + v);
                    sleep(100);
                });

        sleep(5600); // 自带的都是守护线程，不等待会直接退出
    }

    static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

}
