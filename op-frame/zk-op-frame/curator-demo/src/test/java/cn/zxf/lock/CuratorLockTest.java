package cn.zxf.lock;

import cn.zxf.node.NodeOp;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * <a href="https://juejin.im/post/5c01532ef265da61362232ed">加锁原理</a>
 * <p>
 * Created by zengxf on 2018-12-14
 */
@Slf4j
public class CuratorLockTest {

    int count = 0;

    @Test
    public void test1() throws Exception {
        CuratorFramework client = NodeOp.instance.getClient();
        log.info("进入 - " + LocalTime.now());
        InterProcessMutex lock = new InterProcessMutex(client, "/zxf2/test_lock");
        if (lock.acquire(1000, TimeUnit.MILLISECONDS)) {
            try {
                log.info("获锁成功 - " + LocalTime.now());
            } finally {
                lock.release();
            }
        } else {
            log.info("获锁失败 - " + LocalTime.now());
        }
        log.info("退出 - " + LocalTime.now());
    }

    @Test
    public void test2() throws InterruptedException {
        CuratorFramework client = NodeOp.instance.getClient();
        InterProcessMutex lock = new InterProcessMutex(client, "/zxf2/test-mt");
        for (int i = 0; i < 10; i++) {
            Runnable run = () -> {
                try {
                    // 获取互斥锁
                    lock.acquire();
                    for (int j = 0; j < 10; j++) {
                        count++;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    log.info("count = " + count);
                    // 释放互斥锁
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(run, "test-" + i).start();
        }
        Thread.sleep(8_000L);
        log.info("End! count = " + count);
    }


}
