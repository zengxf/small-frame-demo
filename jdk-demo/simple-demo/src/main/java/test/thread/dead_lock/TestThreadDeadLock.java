package test.thread.dead_lock;

// jps
// jstack -l $pId > dead-lock.txt
public class TestThreadDeadLock {
    static Object lock1 = new Object(), lock2 = new Object();

    public static void main( String[] args ) {
        Runnable r1 = () -> {
            synchronized ( lock1 ) {
                sleep( 100 );
                System.out.println( "1-1 done" );
                synchronized ( lock2 ) {
                    sleep( 100 );
                    System.out.println( "1-2 done" );
                }
            }
        };
        Runnable r2 = () -> {
            synchronized ( lock2 ) {
                sleep( 100 );
                System.out.println( "2-1 done" );
                synchronized ( lock1 ) {
                    sleep( 100 );
                    System.out.println( "2-2 done" );
                }
            }
        };
        new Thread( r1, "t-1" ).start();
        new Thread( r2, "t-2" ).start();
    }

    static void sleep( int millis ) {
        try {
            Thread.sleep( millis );
        } catch ( InterruptedException e ) {
        }
    }

}
