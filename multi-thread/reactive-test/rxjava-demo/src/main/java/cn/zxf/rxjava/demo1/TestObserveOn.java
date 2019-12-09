package cn.zxf.rxjava.demo1;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TestObserveOn {

    public static void main( String[] args ) {
        Observable //
                .create( e -> {
                    e.onNext( 1 );
                    e.onNext( 2 );
                    e.onNext( 3 );
                    e.onNext( 4 );
                    e.onComplete();
                } ) //
                .observeOn( Schedulers.single() ) //
                .map( i -> "res: " + i ) //
                .subscribe( s -> System.out.println( Thread.currentThread().getName() + " --> " + s ) );
    }

}
