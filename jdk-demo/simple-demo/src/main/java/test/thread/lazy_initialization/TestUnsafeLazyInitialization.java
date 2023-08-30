package test.thread.lazy_initialization;

import java.util.stream.IntStream;

public class TestUnsafeLazyInitialization {

    public static void main( String[] args ) {
        Runnable r = () -> {
            System.out.println( Thread.currentThread().getName() + "\t" + TestUnsafeLazyInitializationInner.getInstance() );
        };
        IntStream.range( 0, 5 ).forEach( i -> {
            new Thread( r, "test-" + i ).start();
        } );
    }

    static class TestUnsafeLazyInitializationInner {
        private static Instance instance;

        static Instance getInstance() {
            if ( instance == null )
                instance = new Instance();
            return instance;
        }

        static class Instance {
        }
    }

}
