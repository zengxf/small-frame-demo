package test.illustration;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟类的初始化加锁
 * 
 * <p>
 * Created by zengxf on 2018-02-24
 */
public class MockClassInitializeLock {

    public static final int UN_INITIALZATION = 0,                  // 未初始化
            INITIALLING = 1,                                       // 初始化中
            INITIALLED = 2;                                        // 初始化完成

    volatile int            status           = UN_INITIALZATION;
    Lock                    lock             = new ReentrantLock();
    Condition               condition        = lock.newCondition();

    public void initClass() {
        lock.lock(); // 加锁读取 status 状态变量
        switch ( status ) {
            case UN_INITIALZATION:
                boolean qualification = this.initialize(); // 初始化
                if ( qualification ) { // 初始化成功
                    this.doneAndUnlock(); // 完成并释放锁 & 返回
                    return;
                } else { // 还没资格初始化
                    this.waitInitializeDone(); // 等待初始化完成
                    this.doneAndUnlock(); // 完成并释放锁 & 返回
                    return;
                }

            case INITIALLING:
                this.waitInitializeDone(); // 等待初始化完成
                this.doneAndUnlock(); // 完成并释放锁 & 返回
                return;

            case INITIALLED:
                this.doneAndUnlock(); // 完成并释放锁 & 返回
                return;
        }
    }

    private boolean initialize() {
        if ( status != UN_INITIALZATION )
            return false; // 返回

        status = INITIALLING;
        lock.unlock(); // 设置标识后解锁

        this.initializeOp(); // 进行初始化操作

        lock.lock(); // 加锁后再设置标识，然后再通知所有
        status = INITIALLED;
        condition.signalAll();

        return true;
    }

    private void waitInitializeDone() {
        try {
            condition.await();
        } catch ( InterruptedException e ) {
        }
    }

    private void initializeOp() {
    }

    private void doneAndUnlock() {
        lock.unlock();
    }

}
