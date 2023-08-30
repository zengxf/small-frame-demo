package test.thread.lazy_initialization;

import java.util.stream.IntStream;

public class TestClassLocking {

    public static void main( String[] args ) {
        Runnable r = () -> {
            System.out.println( Thread.currentThread().getName() + "\t" + TestClassLockingInner.getInstance() );
        };
        IntStream.range( 0, 5 ).forEach( i -> {
            new Thread( r, "test-" + i ).start();
        } );
    }

    static class TestClassLockingInner {
        private static class InstanceHolder {
            public static Instance instance = new Instance();
        }

        public static Instance getInstance() {
            return InstanceHolder.instance;
        }

        static class Instance {
        }
    }

}
