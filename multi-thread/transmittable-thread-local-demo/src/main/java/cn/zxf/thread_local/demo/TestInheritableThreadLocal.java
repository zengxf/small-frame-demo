package cn.zxf.thread_local.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestInheritableThreadLocal {
    static ThreadLocal<String> local = new InheritableThreadLocal<>();
    static ExecutorService     pool  = Executors.newFixedThreadPool( 2 );

    public static void main( String[] args ) {
        for ( int i = 0; i < 10; i++ ) {
            int j = i;
            pool.execute( new Runnable() {
                @Override
                public void run() {
                    local.set( "zxf-" + j );
                    new Service().call();
                }
            } );
        }
    }

    static class Service {
        public void call() {
            pool.execute( new Runnable() {
                @Override
                public void run() {
                    new Dao().call();
                }
            } );
        }
    }

    static class Dao {
        public void call() {
            System.out.println( "TestInheritableThreadLocal - Dao: " + local.get() );
        }
    }

}
