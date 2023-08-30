package test.jdkapi.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import util.SleepUtils;

/**
 * <pre>
 * ThreadLocal.get() 相对而已比较耗时。
 * 共享锁获取时，设置状态是：c + SHARED_UNIT，这样 c >>> SHARED_SHIFT 就准了；c + SHARED_UNIT 相当于共享标识位的 c++；
 *   头节点不是共享，则等待
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-02-24
 */
public class TestReadWriteLock {

    /**
     * @see ReadWriteLock
     */
    public static void main( String[] args ) {
        // testCommon();
        // testLockUpgrade();
        testLockDegrade();
    }

    // 锁的降级是允许的
    static void testLockDegrade() {
        ReadWriteLock rwl = new ReentrantReadWriteLock();
        Lock readLock = rwl.readLock();
        Lock writeLock = rwl.writeLock();

        writeLock.lock();
        System.out.println( Thread.currentThread()
                .getName() + " write ...." );
        SleepUtils.millisecond( 1000L );
        System.out.println( Thread.currentThread()
                .getName() + " write done!" );

        readLock.lock();
        System.out.println( Thread.currentThread()
                .getName() + " read ...." );
        SleepUtils.millisecond( 1000L );
        System.out.println( Thread.currentThread()
                .getName() + " read done!" );

        readLock.unlock();

        writeLock.unlock();
    }

    // 读写锁不允许锁的升级，不能直接从读锁升级到写锁。
    // 如果读锁还没有释放，此时获取写锁，会导致写锁永久等待，最终导致相关线程都阻塞
    static void testLockUpgrade() {
        ReadWriteLock rwl = new ReentrantReadWriteLock();
        Lock readLock = rwl.readLock();
        Lock writeLock = rwl.writeLock();

        readLock.lock();
        System.out.println( Thread.currentThread()
                .getName() + " read ...." );
        SleepUtils.millisecond( 2000L );
        System.out.println( Thread.currentThread()
                .getName() + " read done!" );

        writeLock.lock();
        System.out.println( Thread.currentThread()
                .getName() + " write ...." );
        SleepUtils.millisecond( 1000L );
        System.out.println( Thread.currentThread()
                .getName() + " write done!" );

        writeLock.unlock();

        readLock.unlock();
    }

    static void testCommon() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        Lock readLock = rwl.readLock();
        Lock writeLock = rwl.writeLock();

        Runnable read = () -> {
            System.out.println( Thread.currentThread()
                    .getName() + " readLock init ...." );
            SleepUtils.millisecond( 1000L );
            readLock.lock(); // Thread.currentThread().getName().equals( "test-read-2" )
            try {
                System.out.println( Thread.currentThread()
                        .getName() + " read ...." );
                SleepUtils.millisecond( 2000L );
                System.out.println( Thread.currentThread()
                        .getName() + " read done!" );
            } finally {
                readLock.unlock();
            }
        };

        Runnable write = () -> {
            SleepUtils.millisecond( 1200L );
            System.out.println( Thread.currentThread()
                    .getName() + " writeLock init ...." );
            writeLock.lock();
            try {
                System.out.println( Thread.currentThread()
                        .getName() + " write ...." );
                SleepUtils.millisecond( 1000L );
                System.out.println( Thread.currentThread()
                        .getName() + " write done!" );
            } finally {
                writeLock.unlock();
            }
        };

        new Thread( read, "test-read-0" ).start();
        new Thread( write, "test-write" ).start();
        SleepUtils.millisecond( 500L );
        new Thread( read, "test-read-2" ).start();
    }

}
