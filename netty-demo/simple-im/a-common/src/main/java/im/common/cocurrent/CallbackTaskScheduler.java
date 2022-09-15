package im.common.cocurrent;

import com.google.common.util.concurrent.*;
import im.common.utils.ThreadUtils;

import java.util.concurrent.ExecutorService;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
public class CallbackTaskScheduler {

    static ListeningExecutorService guavaPool = null;

    static {
        ExecutorService jPool = ThreadUtils.getMixedTargetThreadPool();
        guavaPool = MoreExecutors.listeningDecorator(jPool);
    }


    /*** 添加任务 */
    public static <R> void add(CallbackTask<R> executeTask) {
        ListenableFuture<R> future = guavaPool.submit(() -> {
            R r = executeTask.execute();
            return r;
        });
        Futures.addCallback(future, new FutureCallback<R>() {
            public void onSuccess(R r) {
                executeTask.onBack(r);
            }

            public void onFailure(Throwable t) {
                executeTask.onException(t);
            }
        }, guavaPool);
    }

}
