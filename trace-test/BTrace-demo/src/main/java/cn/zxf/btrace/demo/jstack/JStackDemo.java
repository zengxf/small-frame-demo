package cn.zxf.btrace.demo.jstack;

import java.time.LocalDateTime;
import java.util.ArrayList;

import cn.zxf.btrace.common.Sleep;

public class JStackDemo {
    public static void main( String[] args ) {
        Runnable r = () -> {
            while ( true ) {
                Test.test();
                test();
                new ArrayList<>();
                Sleep.sleep( 1000L );
            }
        };

        Thread t = new Thread( r, "test-run" );
        t.start();
    }

    public static class Test {
        static void test() {
            System.out.println( "test in - " + LocalDateTime.now() );
        }
    }

    public static void test() {
        System.out.println( "test out - " + LocalDateTime.now() );
    }

}
