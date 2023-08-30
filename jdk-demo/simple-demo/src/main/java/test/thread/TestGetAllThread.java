package test.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Map;

public class TestGetAllThread {
    public static void main( String[] args ) {
        Runnable r1 = () -> {
            try {
                synchronized ( "test" ) {
                    System.out.println( "----------------------" );
                    System.out.println( "test-1 interior: " + Thread.holdsLock( "test" ) );
                    System.out.println( "----------------------" );
                    Thread.sleep( 1000L );
                }
            } catch ( InterruptedException e ) {
            }
        };
        Runnable r = () -> {
            try {
                Thread.sleep( 1000L );
            } catch ( InterruptedException e ) {
            }
        };

        Thread t1 = new Thread( r1, "test-1" );
        t1.start();
        new Thread( r, "test-2" ).start();
        new Thread( r, "test-3" ).start();

        printAllThreads();
        System.out.println( "=========================" );
        printThreadsInfo();
        System.out.println( "=========================" );
    }

    static void printAllThreads() {
        System.out.println( "printAllThreads():" );
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for ( Thread t : map.keySet() ) {
            System.out.println( "   " + t );
            for ( StackTraceElement ste : map.get( t ) ) {
                System.out.println( "        " + ste );
            }
        }
    }

    static void printThreadsInfo() {
        System.out.println( "printThreadsInfo():" );
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long ids[] = threadBean.getAllThreadIds();
        Arrays.sort( ids );
        ThreadInfo[] tis = threadBean.getThreadInfo( ids, 2 );
        for ( ThreadInfo ti : tis ) {
            if ( ti == null )
                continue;
            System.out.println( "    Id=" + ti.getThreadId() //
                    + ", state=" + ti.getThreadState() //
                    + ", name=" + ti.getThreadName() );
            for ( StackTraceElement ste : ti.getStackTrace() ) {
                System.out.println( "        " + ste );
            }
        }
    }

}
