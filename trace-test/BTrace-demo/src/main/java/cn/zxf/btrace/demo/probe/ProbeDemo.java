package cn.zxf.btrace.demo.probe;

import java.util.Random;

import cn.zxf.btrace.common.BTraceCommand;

public class ProbeDemo {

    public static void main( String[] args ) {
        Runnable r = () -> {
            while ( true ) {
                test();
                try {
                    Thread.sleep( 1000L );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
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
