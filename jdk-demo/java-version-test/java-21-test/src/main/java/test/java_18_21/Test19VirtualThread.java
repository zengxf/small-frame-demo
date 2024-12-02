package test.java_18_21;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Java 19 虚拟线程
 * <p/>
 * 参考：https://juejin.cn/post/7155406687598280740
 * <p>
 * <p/>
 * Created by ZXFeng on 2024/12/2
 */
public class Test19VirtualThread {

    public static void main(String[] args) throws Exception {
        // simpleTest();
        // builderTest();
        // poolTest();
        useTimeTest();

        sleep(1000);
    }

    /**
     * 用时测试
     */
    static void useTimeTest() throws Exception {
        int testTimes = 10_000;
        final AtomicInteger atomicInteger = new AtomicInteger();

        Runnable runnable = () -> {
            sleep(1000);
            atomicInteger.incrementAndGet();
            // System.out.println("Work Done - " + atomicInteger.incrementAndGet());
        };

        // {
        //     Instant start = Instant.now();
        //     try (var executor = Executors.newFixedThreadPool(100)) {
        //         for (int i = 0; i < testTimes; i++) {
        //             executor.submit(runnable);
        //         }
        //     }
        //     Instant finish = Instant.now();
        //     long timeElapsed = Duration.between(start, finish).toMillis();
        //     System.out.println("平台线程池-总耗时 : " + timeElapsed);
        // }
        // System.out.println("atomicInteger: " + atomicInteger);

        {
            Instant start = Instant.now();
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < testTimes; i++) {
                    executor.submit(runnable);
                }
            }
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("虚拟线程池-总耗时 : " + timeElapsed);
        }
        System.out.println("atomicInteger: " + atomicInteger);

        {
            CountDownLatch latch = new CountDownLatch(testTimes);
            Instant start = Instant.now();
            for (int i = 0; i < testTimes; i++) {
                Thread.startVirtualThread(() -> {
                    runnable.run();
                    latch.countDown();
                });
            }
            latch.await();
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("虚拟线程-总耗时 : " + timeElapsed);
        }
        System.out.println("atomicInteger: " + atomicInteger);
    }

    /**
     * 并不建议虚拟线程和线程池一起使用，因为 Java 线程池的设计是为了避免创建新的操作系统线程的开销，
     * 但是创建虚拟线程的开销并不大，所以其实没必要放到线程池中。
     */
    static void poolTest() {
        long start = System.currentTimeMillis();

        Set<Thread> sets = new HashSet<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    sets.add(Thread.currentThread());
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
            // executor.shutdown(); // 在 close() 方法中会执行
        }

        long use = System.currentTimeMillis() - start;
        System.out.println("用时: " + use + " ms");

        // sets.forEach(t -> System.out.println("t: " + t));
        System.out.println("线程数：" + sets.size());
    }

    static void builderTest() {
        Thread.Builder platformBuilder = Thread.ofPlatform().name("平台线程");
        Thread.Builder virtualBuilder = Thread.ofVirtual().name("虚拟线程");

        Thread t1 = platformBuilder.start(() -> {
            System.out.println("当前线程：" + Thread.currentThread());
            System.out.println("平台线程执行中...");
        });
        System.out.println("t1-status: " + t1.getState());

        Thread t2 = virtualBuilder.start(() -> {
            System.out.println("当前线程：" + Thread.currentThread());
            System.out.println("虚拟线程执行中...");
        });
        System.out.println("t2-status: " + t2.getState());
    }

    static void simpleTest() {
        Thread.startVirtualThread(() -> {
            System.out.println("当前线程：" + Thread.currentThread());
            System.out.println("虚拟线程执行中...");
        });
    }

    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
