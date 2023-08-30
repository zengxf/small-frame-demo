package test.jdkapi.juc.thread_pool;

import lombok.extern.slf4j.Slf4j;
import util.Print;
import util.SleepUtils;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试钩子
 * <br/>
 * Created by ZXFeng on 2022/3/3.
 */
@Slf4j
public class TestHook {

    static ThreadLocal<Long> startTime = ThreadLocal.withInitial(() -> System.currentTimeMillis());

    public static void main(String[] args) {
        ExecutorService pool = new ThreadPoolExecutor(
                2, 4, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(2)
        ) {
            @Override
            protected void terminated() {
                Print.tco("调度器已经终止!");
            }

            @Override
            protected void beforeExecute(Thread t, Runnable target) {
                Print.tco("前钩被执行");
                startTime.set(System.currentTimeMillis());
                super.beforeExecute(t, target);
            }

            @Override
            protected void afterExecute(Runnable target, Throwable t) {
                super.afterExecute(target, t);
                long time = (System.currentTimeMillis() - startTime.get());
                Print.tco("后钩被执行, 任务执行时长（ms）： " + time);
                startTime.remove();
            }
        };
        for (int i = 1; i <= 5; i++) {
            pool.execute(() -> {
                Print.tco("-----");
            });
        }
        SleepUtils.second(3);
        Print.tco("关闭线程池");
        // pool.shutdown();
        pool.shutdownNow();
    }

}
