package test.jdkapi.juc.test_volatile;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;
import util.JdkUtil;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <br/>
 * Created by ZXFeng on 2023/2/12.
 */
@Slf4j
public class TestCAS {

    /**
     * 下面两项测试，说明 CAS 是拿主存中的数据比较，并更新到主存，CPU 机制也使各缓存失效；
     * volatile 使可见性更有保证。
     */
    volatile int value = 0; // ok，失败次数多，是因为屏障太多，相当于 CAS 方法变大，增加失败几率，但能保证非 CAS 线程的可见性
    // int value = 0; // ok! 失败次数甚至还少些！不能保证非 CAS 线程的可见性

    static final long valueOffset;
    static final Unsafe unsafe = JdkUtil.getUnsafe();
    static final AtomicLong failure = new AtomicLong(0);

    static {
        try {
            valueOffset = unsafe.objectFieldOffset(TestCAS.class.getDeclaredField("value"));
            log.info("valueOffset:= " + valueOffset);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    void selfPlus() {
        int oldValue;
        int i = 0;
        do {
            oldValue = value;       // 获取旧值
            if (i++ > 1) {          // 统计无效的自旋次数
                failure.incrementAndGet();
            }
        } while (!cas(oldValue, oldValue + 1));
    }

    boolean cas(int oldValue, int newValue) {
        return unsafe.compareAndSwapInt(this, valueOffset, oldValue, newValue);
    }

    public static void main(String[] args) throws InterruptedException {
        TestCAS target = new TestCAS();

        Runnable r = () -> {
            for (int i = 0; i < 100_0000; i++) {
                // target.value++; // err
                target.selfPlus(); // ok
            }
        };

        Thread t1 = new Thread(r, "T1");
        t1.start();
        Thread t2 = new Thread(r, "T2");
        t2.start();
        Thread t3 = new Thread(r, "T3");
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        log.info("value: [{}]", target.value);
        log.info("failure: [{}]", failure);
    }

}
