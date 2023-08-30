package test.jdkapi.juc;

import java.util.concurrent.Exchanger;

import util.SleepUtils;

/**
 * 两线程相互交换值，直到都交换完返回
 * 
 * <p>
 * Created by zengxf on 2018-02-27
 */
public class TestExchanger {

    public static void main( String[] args ) throws InterruptedException {
        Exchanger<String> ex = new Exchanger<>();
        Runnable ex1 = () -> {
            try {
                System.out.println( Thread.currentThread().getName() + " entry ..." );
                String result = ex.exchange( "test-1" );
                System.out.println( Thread.currentThread().getName() + "\t retuslt: " + result );
            } catch ( InterruptedException e ) {
            }
        };
        Runnable ex2 = () -> {
            try {
                SleepUtils.second( 1 );
                System.out.println( Thread.currentThread().getName() + " entry ..." );
                String result = ex.exchange( "test-2" );
                System.out.println( Thread.currentThread().getName() + "\t retuslt: " + result );
            } catch ( InterruptedException e ) {
            }
        };
        new Thread( ex1, "test-1" ).start();
        new Thread( ex2, "test-2" ).start();
    }

}
