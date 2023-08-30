package test.thread.common_op;

import util.SleepUtils;

/**
 * 不管守护线程执没执行完，都退出
 * 
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestDaemonThread {

    public static void main( String[] args ) {
        Runnable r = () -> {
            System.out.println( "==========" );
            try {
            } finally {
                System.out.println( "========== 1" );
                SleepUtils.second( 1 );
                System.out.println( "========== 2" );
            }
            SleepUtils.second( 1 );
            System.out.println( "========== 0" );
        };
        Thread t = new Thread( r, "" );
        t.setDaemon( true );
        t.start();

        System.out.println( "----" );
    }

}
