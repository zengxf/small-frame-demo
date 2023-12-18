package test19;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <br/>
 * Created by ZXFeng on 2023/2/22.
 */
public class TestThreadPool {

    public static void main(String[] args) {
        // 记录系统线程数
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
            System.out.println(threadInfo.length + " os thread");
        }, 1, 1, TimeUnit.SECONDS);

        long l = System.currentTimeMillis();
        try (var executor = Executors.newFixedThreadPool(20)) {
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

}
