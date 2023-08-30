package test.jdkapi.juc.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * 
 * @author zengxf
 */
public class ThreadPool {

    // 用于定时用的
    private static ScheduledExecutorService taskPool    = Executors.newScheduledThreadPool( 1 );
    // 用于批量用的，带有网络延迟的操作
    private static ExecutorService          commandPool = Executors.newFixedThreadPool( 20 );

    /**
     * 零延迟且以分钟为单位
     */
    public static void scheduledMinute( Runnable task, long minutes ) {
        scheduled0Delay( task, minutes, TimeUnit.MINUTES );
    }

    /**
     * 零延迟
     */
    public static void scheduled0Delay( Runnable task, long period, TimeUnit unit ) {
        scheduled( task, 0L, period, unit );
    }

    public static void scheduled( Runnable task, long initialDelay, long period, TimeUnit unit ) {
        Runnable tryTask = () -> {
            try {
                task.run();
            } catch ( Exception e ) {
                System.out.println( "任务有未捕获的异常：" + e );
            }
        };
        taskPool.scheduleAtFixedRate( tryTask, initialDelay, period, unit );
    }

    public static void execute( Runnable command ) {
        Runnable tryCommand = () -> {
            try {
                command.run();
            } catch ( Exception e ) {
                System.out.println( "命令有未捕获的异常：" + e );
            }
        };
        commandPool.execute( tryCommand );
    }

}
