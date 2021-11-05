package test;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 限流测试：https://juejin.cn/post/6844903783432978439
 * <br/>
 * Created by ZXFeng on  2021/11/5.
 */
@Slf4j
public class TestRateLimiter {

    public static void main(String[] args) throws InterruptedException {
//        testBase();
//        testSmoothBursty();
//        testSmoothWarmingUp();
        testEnough();
    }

    static void testEnough() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(5);
        Thread.sleep(2000L);
        double sign = rateLimiter.acquire(2);
        log.info("sign: [{}]", sign);
        sign = rateLimiter.acquire(2);
        log.info("sign: [{}]", sign);

        sign = rateLimiter.acquire(2);
        log.info("sign: [{}]", sign);
        sign = rateLimiter.acquire(2);
        log.info("sign: [{}]", sign);
    }

    static void testSmoothWarmingUp() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);
        Thread.sleep(2000L);
        double sign = rateLimiter.acquire(20);
        log.info("sign: [{}]", sign);
        sign = rateLimiter.acquire(20);
        log.info("sign: [{}]", sign);
    }

    static void testSmoothBursty() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(5);
        Thread.sleep(2000L);
        double sign = rateLimiter.acquire(20);
        log.info("sign: [{}]", sign);
        sign = rateLimiter.acquire(20);
        log.info("sign: [{}]", sign);
    }

    static void testBase() throws InterruptedException {
        // qps 设置为 5，代表一秒钟只允许处理五个并发请求
        RateLimiter rateLimiter = RateLimiter.create(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        int nTasks = 10;
        CountDownLatch countDownLatch = new CountDownLatch(nTasks);
        long start = System.currentTimeMillis();
        for (int i = 0; i < nTasks; i++) {
            final int j = i;
            executorService.submit(() -> {
                double sign = rateLimiter.acquire(1);
                System.out.println(Thread.currentThread().getName() + " gets job " + j + " start... sign: " + sign);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(Thread.currentThread().getName() + " gets job " + j + " done");
                countDownLatch.countDown();
            });
        }
        executorService.shutdown();
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("10 jobs gets done by 5 threads concurrently in " + (end - start) + " milliseconds");
    }

}
