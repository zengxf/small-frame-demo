package test.jdkapi.juc.lock;

import java.util.concurrent.locks.StampedLock;

import util.SleepUtils;

/**
 * 有乐观策略的读写锁
 * <p>
 * 参考：http://ifeve.com/jdk8中stampedlock原理探究/
 * <p>
 * Created by zengxf on 2018-03-08
 */
public class TestStampedLock {

    public static void main( String[] args ) {
        // test_loop();
        lookUpgrade();
        // lookDegrade();
        // lookDegrade2();
    }

    static void lookUpgrade() {
        StampedLock lock = new StampedLock();
        long stamp = lock.readLock();
        long ws = lock.tryConvertToWriteLock( stamp );
        System.out.println( stamp );
        System.out.println( ws );
        // 升级成功，则更新票据
        if ( ws != 0L ) {
            stamp = ws;
        }
        lock.unlock( stamp );
    }

    static void lookDegrade() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        long ws = lock.tryConvertToReadLock( stamp );
        System.out.println( stamp );
        System.out.println( ws );
        // 降级成功，则更新票据
        if ( ws != 0L ) {
            stamp = ws;
        }
        lock.unlock( stamp );
    }

    static void lookDegrade2() {
        StampedLock lock = new StampedLock();
        // long stamp = lock.writeLock();
        // long stamp = lock.readLock();
        long stamp = lock.tryOptimisticRead();
        long ws = lock.tryConvertToOptimisticRead( stamp ); // 一般情况都会报错
        System.out.println( stamp );
        System.out.println( ws );
        // 降级成功，则更新票据
        if ( ws != 0L ) {
            stamp = ws;
        }
        lock.unlock( stamp );
    }

    static void test_loop() {
        StampedLock sl = new StampedLock();

        Runnable r1 = () -> {
            long stamp = sl.writeLock();
            try { // 写
                System.out.println( "write ..." );
                SleepUtils.second( 2 );
                System.out.println( "write done" );
            } finally {
                sl.unlockWrite( stamp );
            }
        };
        Runnable r2 = () -> {
            long readStamp = sl.tryOptimisticRead();
            if ( !sl.validate( readStamp ) ) // 乐观锁无效，再读
                readStamp = sl.readLock();
            try { // 读
                System.out.println( "read ..." );
            } finally {
                sl.unlockRead( readStamp );
            }
        };
        new Thread( r1, "write" ).start();
        SleepUtils.second( 1 );
        Thread read = new Thread( r2, "read" );
        read.start();
        SleepUtils.second( 1 );
        read.interrupt();
        System.out.println( "read interrupt" );
    }

    static void test_common() {
        StampedLock sl = new StampedLock();

        long stamp = sl.writeLock();
        try { // 写
        } finally {
            sl.unlockWrite( stamp );
        }

        long readStamp = sl.tryOptimisticRead();
        // 读
        if ( !sl.validate( readStamp ) ) {
            readStamp = sl.readLock();
            try { // 乐观锁无效，再读
            } finally {
                sl.unlockRead( readStamp );
            }
        }
    }

}
