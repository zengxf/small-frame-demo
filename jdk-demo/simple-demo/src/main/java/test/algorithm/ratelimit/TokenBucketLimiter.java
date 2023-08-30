package test.algorithm.ratelimit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 令牌桶 限速
 * <br/>
 * ref: https://www.cnblogs.com/crazymakercircle/p/15187184.html
 * <br/>
 * Created by ZXFeng on  2021/11/2.
 */
@Slf4j
public class TokenBucketLimiter {

    // 上一次令牌发放时间
    public static long lastTime = System.currentTimeMillis();
    // 桶的容量
    public static int capacity = 4;
    // 令牌生成速度 /s
    public static int rate = 4;
    // 当前令牌数量
    public static AtomicInteger tokens = new AtomicInteger(0);

    // 返回值说明：
    // false 没有被限制到
    // true 被限流
    public static synchronized boolean isLimited(long taskId, int applyCount) {
        long curTime = System.currentTimeMillis();
        // 时间间隔，单位为 ms
        long gap = curTime - lastTime;
        // 计算时间段内生成的令牌数
        int reversePermits = (int) (gap * rate / 1000);
        // 所有的令牌数 = 当前剩下的 + 新生成的
        int allPermits = tokens.get() + reversePermits;
        // 重置可用的令牌数
        tokens.set(Math.min(capacity, allPermits));
        log.info("tokens {} capacity {} gap {} ", tokens, capacity, gap);
        if (tokens.get() < applyCount) {
            // 若拿不到令牌，则拒绝
            return true;
        } else {
            // 还有令牌，领取令牌
            tokens.getAndAdd(-applyCount);
            // 不是另一个线程匀速放，将就下
            lastTime = curTime; // 生成一个就被拿走了，没不出问题
            return false;
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
        // 同步器
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < turns; j++) {
                        long taskId = Thread.currentThread().getId();
                        boolean intercepted = isLimited(taskId, 1);
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
