package im.common.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
public class ThreadUtils {

    /*** 空闲保活时限，单位秒 */
    private static final int KEEP_ALIVE_SECONDS = 30;
    /*** 有界队列 size */
    private static final int QUEUE_SIZE = 10000;

    /*** 定制的线程工厂 */
    private static class CustomThreadFactory implements ThreadFactory {
        // 线程池数量
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        // 线程数量
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String threadTag;

        CustomThreadFactory(String threadTag) {
            group = Thread.currentThread().getThreadGroup();
            this.threadTag = "appPool-" + poolNumber.getAndIncrement() + "-" + threadTag + "-";
        }

        @Override
        public Thread newThread(Runnable target) {
            Thread t = new Thread(group, target, threadTag + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }


    static class ShutdownHookThread extends Thread {
        private volatile boolean hasShutdown = false;
        private static AtomicInteger shutdownTimes = new AtomicInteger(0);
        private final Callable callback;

        public ShutdownHookThread(String name, Callable callback) {
            super("JVM退出钩子(" + name + ")");
            this.callback = callback;
        }

        @Override
        public void run() {
            synchronized (this) {
                System.out.println(getName() + " starting.... ");
                if (!this.hasShutdown) {
                    this.hasShutdown = true;
                    long beginTime = System.currentTimeMillis();
                    try {
                        this.callback.call();
                    } catch (Exception e) {
                        System.out.println(getName() + " error: " + e.getMessage());
                    }
                    long consumingTimeTotal = System.currentTimeMillis() - beginTime;
                    System.out.println(getName() + "  耗时(ms): " + consumingTimeTotal);
                }
            }
        }
    }

    private static final int MIXED_MAX = 12;  // 最大线程数

    // 懒汉式单例创建线程池：用于混合型任务
    private static class MixedTargetThreadPoolLazyHolder {
        private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
                MIXED_MAX,
                MIXED_MAX,
                KEEP_ALIVE_SECONDS,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_SIZE),
                new CustomThreadFactory("mixed")
        );

        static {
            EXECUTOR.allowCoreThreadTimeOut(true);
            // JVM 关闭时的钩子函数
            Runtime.getRuntime().addShutdownHook(new ShutdownHookThread("混合型任务线程池", () -> {
                // 优雅关闭线程池
                shutdownThreadPoolGracefully(EXECUTOR);
                return null;
            }));
        }
    }

    /*** 获取执行混合型任务的线程池 */
    public static ThreadPoolExecutor getMixedTargetThreadPool() {
        return MixedTargetThreadPoolLazyHolder.EXECUTOR;
    }

    public static void shutdownThreadPoolGracefully(ExecutorService threadPool) {
        if (threadPool == null || threadPool.isTerminated()) {
            return;
        }
        try {
            threadPool.shutdown();   // 拒绝接受新任务
        } catch (SecurityException e) {
            return;
        }
        try {
            // 等待 60s，等待线程池中的任务完成执行
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                // 调用 shutdownNow 取消正在执行的任务
                threadPool.shutdownNow();
                // 再次等待 60s，如果还未结束，可以再次尝试，或则直接放弃
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("线程池任务未正常执行结束");
                }
            }
        } catch (InterruptedException ie) {
            // 捕获异常，重新调用 shutdownNow
            threadPool.shutdownNow();
        }
        // 任然没有关闭，循环关闭 1000 次，每次等待 10 毫秒
        if (!threadPool.isTerminated()) {
            try {
                for (int i = 0; i < 1000; i++) {
                    if (threadPool.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                        break;
                    }
                    threadPool.shutdownNow();
                }
            } catch (Throwable e) {
                System.err.println(e.getMessage());
            }
        }
    }

}