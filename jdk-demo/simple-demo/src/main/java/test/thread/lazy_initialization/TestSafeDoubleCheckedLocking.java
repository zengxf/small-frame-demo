package test.thread.lazy_initialization;

import java.util.stream.IntStream;

public class TestSafeDoubleCheckedLocking {

    public static void main( String[] args ) {
        Runnable r = () -> {
            System.out.println( Thread.currentThread().getName() + "\t" + TestSafeDoubleCheckedLockingInner.getInstance() );
        };
        IntStream.range( 0, 5 ).forEach( i -> {
            new Thread( r, "test-" + i ).start();
        } );
    }

    static class TestSafeDoubleCheckedLockingInner {
        private volatile static Instance instance;

        public static Instance getInstance() {
            if ( instance == null ) {
                synchronized ( TestSafeDoubleCheckedLockingInner.class ) {
                    if ( instance == null )
                        instance = new Instance();
                }
            }
            return instance;
        }

        static class Instance {
        }
    }

}
