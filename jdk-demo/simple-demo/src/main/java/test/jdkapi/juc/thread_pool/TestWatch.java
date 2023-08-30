package test.jdkapi.juc.thread_pool;

import util.SleepUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static util.ThreadInfoUtils.printInfo;

/**
 * 线程监控指标
 * <p>
 * Created by zengxf on 2021-09-08
 */
public class TestWatch {

    public static void main(String[] args) {
        BlockingQueue<Runnable> q = new ArrayBlockingQueue<Runnable>(1);
        ThreadPoolExecutor p = new ThreadPoolExecutor(2, 10, 1, TimeUnit.SECONDS, q);
        submit(p, 4);

        printInfo();
        System.out.println("pool-size: " + p.getPoolSize()); // 现存线程数
        System.out.println("active-size: " + p.getActiveCount()); // 活动线程数
        System.out.println("largest-pool-size: " + p.getLargestPoolSize()); // 曾经创建过的最大线程数量
        System.out.println("max-pool-size: " + p.getMaximumPoolSize());
        System.out.println("queue-size: " + p.getQueue().size());
        System.out.println("total-task-size: " + p.getTaskCount()); // 总提交任务数
        System.out.println("complement-task-size: " + p.getCompletedTaskCount()); // 已完成的任务数
        SleepUtils.second(1);
        System.out.println("----------- 111 -----------\n");

        submit(p, 2);
        printInfo();
        System.out.println("pool-size: " + p.getPoolSize());
        System.out.println("active-size: " + p.getActiveCount());
        System.out.println("largest-pool-size: " + p.getLargestPoolSize());
        System.out.println("queue-size: " + p.getMaximumPoolSize());
        System.out.println("queue-size: " + p.getQueue().size());
        System.out.println("total-task-size: " + p.getTaskCount());
        System.out.println("complement-task-size: " + p.getCompletedTaskCount());
        SleepUtils.second(1);
        System.out.println("----------- 222 -----------\n");

        printInfo();
        System.out.println("pool-size: " + p.getPoolSize());
        System.out.println("active-size: " + p.getActiveCount());
        System.out.println("largest-pool-size: " + p.getLargestPoolSize());
        System.out.println("queue-size: " + p.getMaximumPoolSize());
        System.out.println("queue-size: " + p.getQueue().size());
        System.out.println("total-task-size: " + p.getTaskCount());
        System.out.println("complement-task-size: " + p.getCompletedTaskCount());
        SleepUtils.second(1);
        System.out.println("----------- 333 -----------\n");

        printInfo();
        System.out.println("pool-size: " + p.getPoolSize());
        System.out.println("active-size: " + p.getActiveCount());
        System.out.println("largest-pool-size: " + p.getLargestPoolSize());
        System.out.println("queue-size: " + p.getMaximumPoolSize());
        System.out.println("queue-size: " + p.getQueue().size());
        System.out.println("total-task-size: " + p.getTaskCount());
        System.out.println("complement-task-size: " + p.getCompletedTaskCount());
        System.out.println("----------- 444 -----------\n");

        p.shutdown();
    }

    static void submit(ThreadPoolExecutor p, int size) {
        IntStream.rangeClosed(1, size).forEach(i -> {
            p.execute(() -> {
                System.out.println("" + i + "\t" + Thread.currentThread().getName() + " in ---");
                SleepUtils.second(1);
                System.out.println("" + i + "\t" + Thread.currentThread().getName() + " out ---");
            });
        });
    }

}
