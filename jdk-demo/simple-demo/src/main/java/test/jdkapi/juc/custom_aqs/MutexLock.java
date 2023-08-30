package test.jdkapi.juc.custom_aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <pre>
 * 独占：重写 
 *      tryAcquire(n) 返回 true，表示成功 
 *      和 
 *      tryRelease(n) 返回 true，表示成功
 * 调用：
 *      获取锁：acquire(n)
 *      释放锁：release(n)
 * 注意：
 *      AQS.unparkSuccessor(h) 是唤醒头的下一个节点
 *      AQS.acquireQueued(n, i) 是当前节点满足条件时，直接被唤醒
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-02-24
 */
public class MutexLock implements Lock {
    // 静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -4387327721959839431L;

        // 是否处于占用状态
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 当状态为0的时候获取锁
        public boolean tryAcquire( int acquires ) {
            assert acquires == 1; // Otherwise unused
            if ( compareAndSetState( 0, 1 ) ) {
                setExclusiveOwnerThread( Thread.currentThread() );
                return true;
            }
            return false;
        }

        // 释放锁，将状态设置为0
        protected boolean tryRelease( int releases ) {
            assert releases == 1; // Otherwise unused
            if ( getState() == 0 )
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread( null );
            setState( 0 );
            return true;
        }

        // 返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    // 仅需要将操作代理到Sync上即可
    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire( 1 );
    }

    public boolean tryLock() {
        return sync.tryAcquire( 1 );
    }

    public void unlock() {
        sync.release( 1 );
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly( 1 );
    }

    public boolean tryLock( long timeout, TimeUnit unit ) throws InterruptedException {
        return sync.tryAcquireNanos( 1, unit.toNanos( timeout ) );
    }
}