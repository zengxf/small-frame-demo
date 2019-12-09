package cn.zxf.reactor_demo.flux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestFluxErrorHandle {

    public static void main( String[] args ) throws InterruptedException {
        Flux.just( 1, 2 )
                .concatWith( Mono.error( new IllegalStateException() ) )
                .retry( 1 )
                .subscribe( System.out::println, System.err::println );

        Flux.just( 1, 2 )
                .concatWith( Mono.error( new IllegalStateException() ) )
                .onErrorReturn( 0 )
                .subscribe( System.out::println );

        Flux.just( 1, 2 )
                .concatWith( Mono.error( new IllegalArgumentException() ) )
                .onErrorResume( e -> {
                    if ( e instanceof IllegalStateException ) {
                        return Mono.just( 0 );
                    } else if ( e instanceof IllegalArgumentException ) {
                        return Mono.just( -1 );
                    }
                    return Mono.empty();
                } )
                .subscribe( System.out::println );

    }

}
