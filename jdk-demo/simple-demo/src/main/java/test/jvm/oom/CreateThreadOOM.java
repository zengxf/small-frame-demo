package test.jvm.oom;

import util.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 不停创建线程致 OOM
 * <br/>
 * JVM param:
 * <pre>
 * -Xms20m -Xmx20m -XX:+PrintGC
 * // 只是线程，用 MAT 不好分析
 * -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=heap.bin
 * </pre>
 * <br/>
 * Created by ZXFeng on 2022/9/16.
 */
public class CreateThreadOOM {

    public static void main(String[] args) {
        while (true) {
            SleepUtils.millisecond(1L);
            run();
        }
    }

    private static void run() {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
            });
        }
    }

}
