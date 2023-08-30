package test.thread.common_op;

import static util.ThreadInfoUtils.*;

import util.SleepUtils;

/**
 * 查看所有线程相差信息
 * 
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestThreadInfo {

    static Object lock = new Object();

    public static void main( String[] args ) {
        Runnable r = () -> {
            synchronized ( lock ) {
                SleepUtils.second( 1 );
                try {
                    System.out.println( "--------------- wait ------------" );
                    lock.wait();
                } catch ( InterruptedException e ) {
                }
            }
        };
        Thread t = new Thread( r, "test-01" );
        t.start();

        printInfo();
        SleepUtils.second( 2 );
        System.out.println( "========================" );
        printInfo();
        SleepUtils.second( 2 );
        System.out.println( "========================" );
        printInfo();
    }

}
