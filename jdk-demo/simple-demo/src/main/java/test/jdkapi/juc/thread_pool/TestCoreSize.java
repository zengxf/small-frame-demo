package test.jdkapi.juc.thread_pool;

import static util.ThreadInfoUtils.printInfo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import util.SleepUtils;

/**
 * 提交任务时，才创建 core 线程，不是一开始就创建
 * <p>
 * Created by zengxf on 2019-12-11
 */
public class TestCoreSize {

    public static void main(String[] args) {
        BlockingQueue<Runnable> q = new ArrayBlockingQueue<Runnable>(1);
        ThreadPoolExecutor p = new ThreadPoolExecutor(2, 3, 1, TimeUnit.SECONDS, q);
        IntStream.rangeClosed(1, 1)
                .forEach(i -> {
//                    p.execute(() -> {
//                        System.out.println("==>\t" + i + "\t" + Thread.currentThread().getName() + " in ---");
//                        SleepUtils.second(1);
//                        System.out.println("==>\t" + i + "\t" + Thread.currentThread().getName() + " exit ---");
//                    });
                });
        printInfo();

        System.out.println("pool-size: " + p.getPoolSize());
        System.out.println("active-size: " + p.getActiveCount());
        System.out.println("max-pool-size: " + p.getMaximumPoolSize());
        System.out.println("queue-size: " + p.getQueue().size());
        SleepUtils.second(2);
        System.out.println();
        printInfo();

        p.shutdown();
    }

}
