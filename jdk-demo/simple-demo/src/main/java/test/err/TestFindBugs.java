package test.err;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用 FindBugs 找 bug <br>
 * 下载地址：https://github.com/spotbugs/spotbugs
 * 
 * <p>
 * Created by zengxf on 2017-09-26
 */
public class TestFindBugs {

    static ReentrantLock lock = new ReentrantLock();

    public static void main( String[] args ) {
        for ( int i = 0; i < 10; i++ ) {
            Task task = new Task( lock );
            Thread thread = new Thread( task );
            thread.run();
        }
    }

    public static class Task implements Runnable {
        private ReentrantLock lock;

        public Task( ReentrantLock lock ) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep( 1 );
                lock.unlock();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }
}
