package cn.zxf.rxjava.demo1;

import io.reactivex.rxjava3.core.Observable;

public class TestDemo1 {

    public static void main( String[] args ) {
        Observable.just( 1, 2, 3, 4, 5, 6 ) //
                .doOnNext( System.out::println ) //
                .filter( i -> i % 2 == 0 ) //
                .doOnNext( System.out::println ) //
                .map( i -> Math.sqrt( i ) ) //
                .reduce( ( r, d ) -> r + d ) //
                .map( r -> r / 3 ) //
                .subscribe( System.out::println );
    }

}
