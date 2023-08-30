package test.thread.common_op;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import util.SleepUtils;

/**
 * notifyAll() 后 wait() 等待的线程要继续抢锁 <br>
 * wait() 后状态为 BLOCKED，但 jstack 比较难查看到 <br>
 * notifyAll() 后状态为 WAITING <br>
 * sleep(n) 后状态为 TIMED_WAITING，不会释放锁
 * 
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestObjectWait {
    static Object lock = new Object();

    public static void main( String[] args ) throws Exception {
        System.out.println( "jstack " + ManagementFactory.getRuntimeMXBean().getName().split( "@" )[0] );

        Thread waitThread = new Thread( new Wait(), "WaitThread" );
        waitThread.start();
        TimeUnit.SECONDS.sleep( 1 );

        Thread notifyThread = new Thread( new Notify(), "NotifyThread" );
        notifyThread.start();
    }

    static class Wait implements Runnable {
        public void run() {
            synchronized ( lock ) {
                try {
                    System.out.println( Thread.currentThread() + " ###### " );
                    SleepUtils.second( 5 );
                    System.out.println( Thread.currentThread() + " ###### 2 " );
                    lock.wait();
                    System.out.println( Thread.currentThread() + " ###### 3 " );
                } catch ( InterruptedException e ) {
                }
                System.out.println( Thread.currentThread() + " ########### " );
            }
        }
    }

    static class Notify implements Runnable {
        public void run() {
            synchronized ( lock ) {
                System.out.println( Thread.currentThread() + " ---------- " );
                SleepUtils.second( 5 );
                System.out.println( Thread.currentThread() + " ---------- 1 " );
                lock.notifyAll();
                System.out.println( Thread.currentThread() + " ---------- 1 1 " );
                SleepUtils.second( 5 );
            }
            synchronized ( lock ) {
                System.out.println( Thread.currentThread() + " --------- 2 " );
                SleepUtils.second( 5 );
            }
        }
    }

}
