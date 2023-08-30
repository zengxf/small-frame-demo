package test.thread.stack;

import java.lang.management.ManagementFactory;
import java.util.stream.IntStream;

/**
 * <pre>
 * 导出：jstack 21400 > thread.log
 * 在线分析：http://fastthread.io
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestMultilThreadLoop {
    public static void main( String[] args ) {
        System.out.println( ManagementFactory.getRuntimeMXBean()
                .getName() );

        IntStream.rangeClosed( 1, 5 )
                .forEach( i -> {
                    Thread t = new Thread( new Run() );
                    t.setName( "test-zxf-" + i );
                    t.start();
                } );
        while ( true ) {
            kill();
        }
    }

    static class Run implements Runnable {
        public void run() {
            while ( true ) {
                test();
            }
        }

        void test() {
            fuck();
        }

        void fuck() {
            shit();
        }
    }

    static void shit() {
        kill();
    }

    static void kill() {
        try {
            Thread.sleep( 1L );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }
}
