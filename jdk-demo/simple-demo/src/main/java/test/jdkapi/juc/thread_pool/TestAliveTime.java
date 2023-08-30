package test.jdkapi.juc.thread_pool;

import static util.ThreadInfoUtils.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import util.SleepUtils;

/**
 * 超出 core 的线程，会执行完任务后最多存活 keepAliveTime，然后销毁
 * <p>
 * Created by zengxf on 2018-02-28
 */
public class TestAliveTime {

    public static void main(String[] args) {
        BlockingQueue<Runnable> q = new ArrayBlockingQueue<Runnable>(1);
        ThreadPoolExecutor p = new ThreadPoolExecutor(2, 3, 1, TimeUnit.SECONDS, q);
        p.allowCoreThreadTimeOut(true);
        IntStream.rangeClosed(1, 4).forEach(i -> {
            p.execute(() -> {
                System.out.println("" + i + "\t" + Thread.currentThread().getName() + " in ---");
                SleepUtils.second(1);
                System.out.println("" + i + "\t" + Thread.currentThread().getName() + " out ---");
            });
        });

        printInfo();
        System.out.println("pool-size: " + p.getPoolSize()); // 线程数
        System.out.println("active-size: " + p.getActiveCount()); // 活动数，并非线程数
        System.out.println("queue-size: " + p.getQueue().size());
        SleepUtils.second(1);
        System.out.println("----------- 111 -----------\n");

        printInfo();
        System.out.println("pool-size: " + p.getPoolSize()); // 线程数
        System.out.println("active-size: " + p.getActiveCount());
        System.out.println("queue-size: " + p.getQueue().size());
        SleepUtils.second(1);
        System.out.println("----------- 222 -----------\n");

        printInfo();
        System.out.println("pool-size: " + p.getPoolSize()); // 线程数
        System.out.println("active-size: " + p.getActiveCount());
        System.out.println("queue-size: " + p.getQueue().size());
        System.out.println("----------- 333 -----------\n");

        p.shutdown();
    }

}
