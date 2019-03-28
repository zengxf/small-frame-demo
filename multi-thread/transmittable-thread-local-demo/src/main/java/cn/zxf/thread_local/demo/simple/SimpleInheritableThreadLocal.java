package cn.zxf.thread_local.demo.simple;

public class SimpleInheritableThreadLocal {
    static ThreadLocal<String> local = new InheritableThreadLocal<>();

    public static void main( String[] args ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                local.set( "zxf-01" );
                new Service().call();
            }
        } ).start();
    }

    static class Service {
        public void call() {
            System.out.println( "Service:" + Thread.currentThread()
                    .getName() );
            System.out.println( "Service:" + local.get() );

            new Thread( new Runnable() {
                @Override
                public void run() {
                    new Dao().call();
                }
            } ).start();
        }
    }

    static class Dao {
        public void call() {
            System.out.println( "==========================" );
            System.out.println( "Dao:" + Thread.currentThread()
                    .getName() );
            System.out.println( "Dao:" + local.get() );
        }
    }
}
