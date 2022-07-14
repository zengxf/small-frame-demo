package cn.zxf.rxjava.demo1;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/11.
 */
@Slf4j
public class TestBackPressure {

    public static void main(String[] args) throws InterruptedException {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        for (int i = 0; i < 256; i++) {
                            emitter.onNext(i);
                            if (i > 200) {
                                log.info("----> {}", i);
                            }
                        }
                    }
                }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        log.info("onSubscribe");
                        //s.request(64);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log.info("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        log.info("onComplete");
                    }
                });
        Thread.sleep(Integer.MAX_VALUE);
    }

}
