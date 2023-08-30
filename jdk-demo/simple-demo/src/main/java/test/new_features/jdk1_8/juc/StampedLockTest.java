package test.new_features.jdk1_8.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

class StampedLockTest extends Util {

    static int count = 0;

    public static void main( String[] args ) throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool( 2 );
        StampedLock lock = new StampedLock();
        executor.submit( () -> {
            long stamp = lock.readLock();
            try {
                if ( count == 0 ) {
                    stamp = lock.tryConvertToWriteLock( stamp ); // 读锁转换为写锁而不用再次解锁和加锁十分实用
                    if ( stamp == 0L ) {
                        System.out.println( "Could not convert to write lock" );
                        stamp = lock.writeLock(); // 阻塞当前线程，直到有可用的写锁
                    }
                    count = 23;
                }
                System.out.println( count );
            } finally {
                lock.unlock( stamp );
            }
        } );
        stop( executor );
    }

    // 测试乐观锁
    // * 乐观锁在刚刚拿到锁之后是有效的。写锁而无需等待乐观的读锁被释放。
    // * 当写锁释放时，乐观的读 锁还处于无效状态。 所以在使用乐观锁时，你需要每次在访问任何共享可变变量之后都要检查锁，来确 保读锁仍然有效
    static void testOptimisticRead() {
        ExecutorService executor = Executors.newFixedThreadPool( 2 );
        StampedLock lock = new StampedLock();
        executor.submit( () -> {
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println( "Optimistic Lock Valid: " + lock.validate( stamp ) );
                sleep( 1 );
                System.out.println( "Optimistic Lock Valid: " + lock.validate( stamp ) );
                sleep( 2 );
                System.out.println( "Optimistic Lock Valid: " + lock.validate( stamp ) );
                sleep( 2 );
                System.out.println( "Optimistic Lock Valid: " + lock.validate( stamp ) );
            } finally {
                lock.unlock( stamp );
            }
        } );
        executor.submit( () -> {
            long stamp = lock.writeLock();
            try {
                System.out.println( "Write Lock acquired" );
                sleep( 2 );
            } finally {
                lock.unlock( stamp );
                System.out.println( "Write done" );
            }
        } );
        stop( executor );
    }

}
