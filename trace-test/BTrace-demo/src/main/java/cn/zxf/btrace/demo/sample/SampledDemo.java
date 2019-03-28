package cn.zxf.btrace.demo.sample;

import java.util.Random;

import cn.zxf.btrace.common.BTraceCommand;
import cn.zxf.btrace.common.Sleep;

public class SampledDemo {

    public static void main( String[] args ) {
        Runnable r = () -> {
            while ( true ) {

                test();

                Sleep.sleep( 1000L );
            }
        };

        Thread t = new Thread( r, "test-run" );
        t.start();

        BTraceCommand.execute();
    }

    public static void test() {
        try {
            long millis = 500L + new Random().nextInt( 500 );
            System.out.println( "sleep: " + millis + " ms" );
            Thread.sleep( millis );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

}
