package test.algorithm.ratelimit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 漏桶 限流
 * <br/>
 * ref: https://www.cnblogs.com/crazymakercircle/p/15187184.html
 * <br/>
 * Created by ZXFeng on  2021/11/2.
 */
@Slf4j
public class LeakBucketLimiter {

    // 计算的起始时间
    private static long lastOutTime = System.currentTimeMillis();
    // 流出速率 每秒 2 次
    private static int leakRate = 2;
    // 桶的容量
    private static int capacity = 2;
    // 剩余的水量
    private static AtomicInteger water = new AtomicInteger(0);

    //返回值说明：
    // false 没有被限制到
    // true 被限流
    public static synchronized boolean isLimit(long taskId, int turn) {
        // 如果是空桶，就当前时间作为漏出的时间
        if (water.get() == 0) {
            lastOutTime = System.currentTimeMillis();
            water.addAndGet(1);
            return false;
        }
        long curTime = System.currentTimeMillis();
        // 执行漏水
        // 泄漏的水 = 时间 * 速度
        int waterLeaked = ((int) ((curTime - lastOutTime) / 1000)) * leakRate;
        // 计算剩余水量 = 桶里面的水 - 泄漏的水
        int waterLeft = water.get() - waterLeaked;
        water.set(Math.max(0, waterLeft)); // 重新设置桶的水量
        // 重新更新 leakTimeStamp
        lastOutTime = curTime;
        // 尝试加水，并且水还未满，放行
        if ((water.get()) < capacity) {
            water.addAndGet(1);
            return false;
        } else { // 水满，拒绝加水，限流
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 线程池，用于多线程模拟测试
        ExecutorService pool = Executors.newFixedThreadPool(10);
        // 被限制的次数
        AtomicInteger limited = new AtomicInteger(0);
        // 线程数
        final int threads = 2;
        // 每条线程的执行轮数
        final int turns = 20;
        // 线程同步器
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < turns; j++) {
                        long taskId = Thread.currentThread().getId();
                        boolean intercepted = isLimit(taskId, j);
                        if (intercepted) {
                            // 被限制的次数累积
                            limited.getAndIncrement();
                        }
                        Thread.sleep(200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 等待所有线程结束
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        float time = (System.currentTimeMillis() - start) / 1000F;
        // 输出统计结果

        log.info("限制的次数为：" + limited.get() + ", 通过的次数为：" + (threads * turns - limited.get()));
        log.info("限制的比例为：" + (float) limited.get() / (float) (threads * turns));
        log.info("运行的时长为：" + time);

        pool.shutdown();
    }

}
