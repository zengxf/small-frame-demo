package test.new_features.jdk1_7.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 如果任务的耗时很平均，则此时 Work-Stealing 并不适合，<br>
 * 因为窃取任务时也是需要抢占锁的，这会造成额外的时间消耗，而且每个线程维护双端队列也会造成更大的内存消耗
 * 
 * <p>
 * Created by zxf on 2017-07-03
 */
public class WorkSteelingTest {

    public static void main( String[] args ) throws Exception {
	testForkJoinPool();
	testThreadPoolExecutor();
    }

    private static final int TASK_NUM	= 100;
    private static final int THREAD_NUM	= 4;

    public static void testForkJoinPool() throws InterruptedException {
	ForkJoinPool forkJoinPool = new ForkJoinPool( THREAD_NUM );

	long startTime = System.nanoTime();

	for ( int i = 1; i <= TASK_NUM; i++ ) {
	    SimpleAction task = new SimpleAction( i * 1 );
	    forkJoinPool.execute( task );
	}

	forkJoinPool.shutdown();
	forkJoinPool.awaitTermination( Long.MAX_VALUE, TimeUnit.MILLISECONDS );

	System.out.println( "被窃取的任务数量：" + forkJoinPool.getStealCount() );

	long finishedTime = System.nanoTime();
	double time = ( finishedTime - startTime ) / 1E9;

	System.out.printf( "%-18s 用时: %.3f 秒\n\n", "ForkJoinPool", time );
    }

    public static void testThreadPoolExecutor() throws InterruptedException {
	ExecutorService threadPool = Executors.newFixedThreadPool( THREAD_NUM );

	long startTime = System.nanoTime();

	for ( int i = 1; i <= TASK_NUM; i++ ) {
	    SimpleRunnable task = new SimpleRunnable( i * 1 );
	    threadPool.execute( task );
	}

	threadPool.shutdown();
	threadPool.awaitTermination( Long.MAX_VALUE, TimeUnit.MILLISECONDS );

	long finishedTime = System.nanoTime();
	double time = ( finishedTime - startTime ) / 1E9;

	System.out.printf( "%-18s 用时: %.3f 秒\n\n", "ThreadPoolExecutor", time );
    }

    @SuppressWarnings( "serial" )
    static class SimpleAction extends RecursiveAction {

	private final int sleepTime;

	public SimpleAction( int sleepTime ) {
	    this.sleepTime = sleepTime;
	}

	@Override
	protected void compute() {
	    if ( sleepTime <= 10 ) {
		try {
		    Thread.sleep( sleepTime );
		} catch ( InterruptedException ex ) {
		    ex.printStackTrace( System.err );
		}
	    } else {
		int sleepTime1 = sleepTime / 2;
		// (sleepTime & 1) == 0 表示 sleepTime 为偶数
		int sleepTime2 = ( sleepTime & 1 ) == 0 ? sleepTime1 : sleepTime1 + 1;

		SimpleAction subTask1 = new SimpleAction( sleepTime1 );
		SimpleAction subTask2 = new SimpleAction( sleepTime2 );

		subTask1.fork();
		subTask2.fork();

		subTask1.join();
		subTask2.join();
	    }
	}
    }

    static class SimpleRunnable implements Runnable {

	private final int sleepTime;

	public SimpleRunnable( int sleepTime ) {
	    this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
	    try {
		Thread.sleep( sleepTime );
	    } catch ( InterruptedException ex ) {
		ex.printStackTrace( System.err );
	    }
	}
    }

}