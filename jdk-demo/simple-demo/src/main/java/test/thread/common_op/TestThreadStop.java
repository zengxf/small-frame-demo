package test.thread.common_op;

import util.SleepUtils;

/**
 * 直接退出，不会释放连接资源
 * 
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestThreadStop {

    @SuppressWarnings( "deprecation" )
    public static void main( String[] args ) {
        Runnable r = () -> {
            System.out.println( "-----------" );
            SleepUtils.second( 1 );
            System.out.println( "-----------" );
            SleepUtils.second( 1 );
            System.out.println( "-----------" );
            SleepUtils.second( 1 );
            System.out.println( "-----------" );
        };

        Thread t = new Thread( r, "test-01" );
        t.start();

        SleepUtils.second( 2 );

        t.stop();
        System.out.println( "=================" );
    }

}
