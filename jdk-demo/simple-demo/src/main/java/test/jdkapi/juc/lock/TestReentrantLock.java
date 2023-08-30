package test.jdkapi.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link Condition#await()} 和 {@link Condition#signal()}是一对，相当于： {@link Object#wait()} 和 {@link Object#notify()}
 * 
 * <p>
 * Created by zengxf on 2018-02-22
 */
public class TestReentrantLock {

    public static void main( String[] args ) throws InterruptedException {
        testCondition();
    }

    static void testCondition() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition cd = lock.newCondition();

        Runnable rAwait = () -> {
            lock.lock();
            try {
                System.out.println( "await" );
                cd.await();
                System.out.println( "await run..." );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        Runnable rNotfiy = () -> {
            lock.lock();
            try {
                System.out.println( "notify" );
                cd.signal();
            } finally {
                lock.unlock();
            }
        };

        new Thread( rAwait, "test-await" ).start();
        Thread.sleep( 1000L );
        new Thread( rNotfiy, "test-notify" ).start();
    }

    static void testCommon() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println( "test" );
        } finally {
            lock.unlock();
        }
    }

}
