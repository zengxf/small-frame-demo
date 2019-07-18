package cn.zxf.btrace.demo.mreturn;

import java.time.LocalTime;

import cn.zxf.btrace.common.Sleep;

public class ReturnDemo {
    public static void main( String[] args ) {
        Runnable r = () -> {
            while ( true ) {
                test();
                Sleep.sleep( 1000L );
            }
        };
        Thread t = new Thread( r, "test-run" );
        t.start();
    }

    static byte[] test() {
        System.out.println( "test out - " + LocalTime.now() );
        return new byte[] { 1, 2, 3 };
    }

}
