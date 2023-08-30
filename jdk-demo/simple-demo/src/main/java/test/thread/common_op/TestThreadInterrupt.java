package test.thread.common_op;

import java.util.concurrent.TimeUnit;

import util.SleepUtils;

/**
 * 线程退出，isInterrupted() 为 false <br>
 * 抛出 InterruptedException 时会清除标识，重新为 false <br>
 * 静态方法 Thread.interrupted() 复位标识，重新为 false
 * 
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestThreadInterrupt {

    public static void main( String[] args ) throws Exception {
        // sleepThread不停的尝试睡眠
        Thread sleepThread = new Thread( new SleepRunner(), "SleepThread" );
        sleepThread.setDaemon( true );
        // busyThread不停的运行
        Thread busyThread = new Thread( new BusyRunner(), "BusyThread" );
        busyThread.setDaemon( true );
        sleepThread.start();
        busyThread.start();

        // 休眠5秒，让sleepThread和busyThread充分运行
        TimeUnit.SECONDS.sleep( 3 );
        System.out.println( "=======================" );
        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println( "SleepThread interrupted is " + sleepThread.isInterrupted() );
        System.out.println( "BusyThread interrupted is " + busyThread.isInterrupted() );

        // 防止sleepThread和busyThread立刻退出
        TimeUnit.SECONDS.sleep( 2 );

        System.out.println( "SleepThread interrupted is " + sleepThread.isInterrupted() );
        System.out.println( "BusyThread interrupted is " + busyThread.isInterrupted() );
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while ( true ) {
                SleepUtils.second( 1 );
                System.out.println( "----- " + Thread.interrupted() );
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while ( true ) {
            }
        }
    }

}
