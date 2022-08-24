package cn.zxf.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/24.
 */
@Slf4j
public class MyZkLockTest {

    int count = 0;

    @Test
    public void lock() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Runnable run = () -> {
                // 创建锁
                MyZkLock lock = new MyZkLock();
                lock.lock();
                // 每条线程，执行 10 次累加
                for (int j = 0; j < 10; j++) {
                    // 公共的资源变量累加
                    count++;
                }
                lock.lock();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                lock.unlock();
                log.info("count = " + count);
                // 释放锁
                lock.unlock();
            };
            new Thread(run, "test-" + i).start();
        }
        Thread.sleep(8_000L);
        log.info("End! count = " + count);
    }

}