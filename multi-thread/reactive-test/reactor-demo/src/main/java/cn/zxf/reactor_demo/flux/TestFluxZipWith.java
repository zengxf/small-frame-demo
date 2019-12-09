package cn.zxf.reactor_demo.flux;

import reactor.core.publisher.Flux;

public class TestFluxZipWith {

    public static void main( String[] args ) {
        Flux.just( "a", "b" )
                .zipWith( Flux.just( "c" ) )
                .subscribe( System.out::println );
        Flux.just( "a", "b", "f" )
                .zipWith( Flux.just( "c", "d" ), ( s1, s2 ) -> String.format( "%s-%s", s1, s2 ) )
                .subscribe( System.out::println );
    }

}
