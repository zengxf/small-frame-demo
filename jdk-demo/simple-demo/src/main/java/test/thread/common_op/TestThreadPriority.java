package test.thread.common_op;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 线程优先级，Windows 下有效
 * 
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestThreadPriority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd   = true;

    public static void main( String[] args ) throws Exception {
        List<Job> jobs = new ArrayList<Job>();

        for ( int i = 0; i < 10; i++ ) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job( priority );
            jobs.add( job );
            Thread thread = new Thread( job, "Thread:" + i );
            thread.setPriority( priority );
            thread.start();
        }

        notStart = false;
        Thread.currentThread().setPriority( 5 );
        System.out.println( "done." );

        TimeUnit.SECONDS.sleep( 2 );
        notEnd = false;

        for ( Job job : jobs ) {
            System.out.println( "Job Priority : " + job.priority + ", Count : " + job.jobCount );
        }
        System.out.println( "==================" );
        
        double avgLow = jobs.stream().limit( 5 ) //
                .peek( job -> System.out.println( "Job Priority : " + job.priority + ", Count : " + job.jobCount ) ) //
                .mapToLong( job -> job.jobCount ).average().getAsDouble();
        System.out.println( "==================" );
        
        double avgHigh = jobs.stream().skip( 5 ) //
                .peek( job -> System.out.println( "Job Priority : " + job.priority + ", Count : " + job.jobCount ) ) //
                .mapToLong( job -> job.jobCount ).average().getAsDouble();
        System.out.println( "==================" );
        
        System.out.println( "avgLow: " + avgLow + " avgHigh: " + avgHigh + " differ: " + ( avgHigh - avgLow ) );
    }

    static class Job implements Runnable {
        private int  priority;
        private long jobCount;

        public Job( int priority ) {
            this.priority = priority;
        }

        public void run() {
            while ( notStart ) {
                Thread.yield();
            }
            while ( notEnd ) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}
