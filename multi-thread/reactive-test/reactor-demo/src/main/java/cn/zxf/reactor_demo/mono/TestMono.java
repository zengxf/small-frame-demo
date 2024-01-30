package cn.zxf.reactor_demo.mono;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class TestMono {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--");
        Mono.delay(Duration.ofMillis(1000))
                .subscribe(v -> log.info("v: [{}]", v));
        Thread.sleep(2000);
    }

}
