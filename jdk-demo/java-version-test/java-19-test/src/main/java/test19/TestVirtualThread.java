package test19;

import java.time.LocalDateTime;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 协程（虚拟线程）：https://heapdump.cn/article/4592243
 * <br/>
 * Created by ZXFeng on 2023/2/22.
 */
public class TestVirtualThread {

    public static void main(String[] args) throws InterruptedException {
        // testMisc();
        // testSleep();
        // testPool();
        // testMulti();
        testThreadLocal();
    }

    static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    public static void getThreadLocal(String val) {
        stringThreadLocal.set(val);
        System.out.println(stringThreadLocal.get());
    }

    static void testThreadLocal() throws InterruptedException {
        Thread testVT1 = Thread.ofVirtual().name("testVT1").unstarted(() -> getThreadLocal("testVT1 local var"));
        Thread testVT2 = Thread.ofVirtual().name("testVT2").unstarted(() -> getThreadLocal("testVT2 local var"));

        testVT1.start();
        testVT2.start();

        System.out.println(stringThreadLocal.get());
        stringThreadLocal.set("main local var");
        System.out.println(stringThreadLocal.get());

        testVT1.join();
        testVT2.join();
    }

    static void testMulti() throws InterruptedException {
        var threads = IntStream.range(0, 15)
                .mapToObj(index -> Thread.ofVirtual().name("v-" + index).unstarted(() -> {
                    System.out.println(Thread.currentThread());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // System.out.println(Thread.currentThread());
                }))
                .toList();

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }

    static void testPool() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
            System.out.println(threadInfo.length + " os thread");
        }, 1, 1, TimeUnit.SECONDS);

        long l = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 200).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    System.out.println(i);
                    return i;
                });
            });
            // executor.shutdown();
        }

        System.out.printf("耗时：%d ms\n", System.currentTimeMillis() - l);
    }

    static void testMisc() {
        // 输出线程 ID 包括虚拟线程和系统线程 Thread.getId() 从 jdk19 废弃
        Runnable runnable = () -> {
            Thread t = Thread.currentThread();
            System.out.println("-----------------------------");
            System.out.println(t.getName() + "\t" + t.threadId());
            System.out.println("-----------------------------");
        };
        // 创建虚拟线程
        Thread testVT = Thread.ofVirtual().name("testVT").unstarted(runnable);
        testVT.start();
        // 创建虚平台线程
        Thread testPT = Thread.ofPlatform().name("testPT").unstarted(runnable);
        testPT.start();
    }

    static void testSleep() throws InterruptedException {
        // 输出线程 ID 包括虚拟线程和系统线程 Thread.getId() 从 jdk19 废弃
        Runnable runnable = () -> {
            Thread t = Thread.currentThread();
            System.out.println("-----------------------------");
            System.out.println(t.getName() + "\t" + t.threadId());
            System.out.println(LocalDateTime.now());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }
            System.out.println(LocalDateTime.now());
            System.out.println("-----------------------------");
        };
        // 创建虚拟线程
        Thread testVT = Thread.ofVirtual().name("testVT").unstarted(runnable);
        testVT.start();

        testVT.join();
    }

}
