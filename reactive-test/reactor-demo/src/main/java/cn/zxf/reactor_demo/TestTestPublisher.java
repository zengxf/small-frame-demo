package cn.zxf.reactor_demo;

import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class TestTestPublisher {

    public static void main( String[] args ) {
        TestPublisher<String> testPublisher = TestPublisher.create();
        testPublisher.next( "a" );
        testPublisher.next( "b" );
        testPublisher.complete();

        StepVerifier.create( testPublisher )
                .expectNext( "a" )
                .expectNext( "b" )
                .expectComplete();
    }

}
