package cn.zxf.reactor_demo.mono;

import java.time.Duration;

import reactor.core.publisher.Mono;

public class TestMono {

    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "--" );
        Mono.delay( Duration.ofMillis( 1000 ) )
                .subscribe( System.out::println );
        Thread.sleep( 2000 );
    }

}
