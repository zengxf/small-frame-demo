package cn.zxf.thread.cpu_schedule;

import java.util.Random;

public class SimpleCompute {

    public static void main( String[] args ) throws InterruptedException {
        Runnable target = () -> {
            Random r = new Random();
            while ( true ) {
                r.nextDouble();
                // Math.random();
            }
        };
        new Thread( target, "线程1" ).start();
        new Thread( target, "线程2" ).start();
        new Thread( target, "线程3" ).start();
        new Thread( target, "线程4" ).start();
    }

}
