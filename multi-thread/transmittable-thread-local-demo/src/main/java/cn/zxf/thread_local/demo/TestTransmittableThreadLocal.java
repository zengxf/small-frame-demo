package cn.zxf.thread_local.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

public class TestTransmittableThreadLocal {
    static TransmittableThreadLocal<String> local = new TransmittableThreadLocal<>();
    static ExecutorService                  pool  = TtlExecutors.getTtlExecutorService( Executors.newFixedThreadPool( 2 ) );

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
            System.out.println( "TestTransmittableThreadLocal - Dao: " + local.get() );
        }
    }

}
