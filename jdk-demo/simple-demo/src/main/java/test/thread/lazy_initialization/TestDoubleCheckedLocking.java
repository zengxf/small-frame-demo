package test.thread.lazy_initialization;

import java.util.stream.IntStream;

import util.SleepUtils;

public class TestDoubleCheckedLocking {

    public static void main( String[] args ) {
        Runnable r = () -> {
            System.out.println( Thread.currentThread().getName() + "\t" + TestDoubleCheckedLockingInner.getInstance() );
        };
        IntStream.range( 0, 5 ).forEach( i -> {
            new Thread( r, "test-" + i ).start();
        } );
    }

    static class TestDoubleCheckedLockingInner { // 1
        private static Instance instance; // 2

        static Instance getInstance() { // 3
            if ( instance == null ) { // 4:第一次检查
                synchronized ( TestDoubleCheckedLockingInner.class ) { // 5:加锁
                    if ( instance == null ) // 6:第二次检查
                        instance = new Instance(); // 7:问题的根源出在这里
                } // 8
            } // 9
            return instance; // 10
        } // 11

        static class Instance {

            String thread;
            int    common_i;

            Instance() {
                SleepUtils.second( 1 );
                this.thread = Thread.currentThread().getName();
                this.common_i = 1;
            }

            public String toString() {
                return super.toString() + " " + thread + " " + common_i;
            }
        }
    }

}
