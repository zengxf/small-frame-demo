package cn.zxf.reactor_demo;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TestStepVerifier {

    public static void main(String[] args) {
        StepVerifier.withVirtualTime(
                        () -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1))
                                .take(2)
                )
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(4))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .verifyComplete();
    }

}
