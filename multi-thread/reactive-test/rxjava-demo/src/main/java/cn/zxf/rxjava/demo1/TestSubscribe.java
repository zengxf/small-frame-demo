package cn.zxf.rxjava.demo1;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSubscribe {

    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() { // 第一步：初始化Observable
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                log.info("Observable emit 1" + "\n");
                e.onNext(1);
                log.info("Observable emit 2" + "\n");
                e.onNext(2);
                log.info("Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();
                log.info("Observable emit 4" + "\n");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() { // 第三步：订阅

            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                i++;
                log.info("observer-value: {}, i: {}", integer, i);
                if (i == 4) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                log.info("onError : value : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                log.info("onComplete" + "\n");
            }
        });

    }

}
