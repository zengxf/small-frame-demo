package cn.zxf.lock;

import cn.zxf.node.NodeOp;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 存在多个线程重用问题；不存在后第 n 个断连问题，
 * 但并不严谨！
 * <br/>
 * Created by ZXFeng on 2022/8/24.
 */
@Slf4j
public class MyZkLock {

    private static final String ZK_PATH = "/test/lock";
    private static final String LOCK_PREFIX = ZK_PATH + "/lk-";
    private static final long WAIT_TIME = 1000;

    CuratorFramework client;
    private String locked_short_path = null;
    private String locked_path = null;
    private String prior_path = null;
    final AtomicInteger lockCount = new AtomicInteger(0);
    private Thread thread;

    public MyZkLock() {
        client = NodeOp.instance.getClient();
        synchronized (client) {
            if (!NodeOp.instance.isNodeExist(ZK_PATH)) {
                NodeOp.instance.createNode(ZK_PATH, "001");
            }
        }
    }

    public boolean lock() {
        // 可重入，确保同一线程，可以重复加锁
        synchronized (this) {
            if (lockCount.get() == 0) {
                thread = Thread.currentThread();
                lockCount.incrementAndGet();
            } else {
                if (!thread.equals(Thread.currentThread())) {
                    return false; // 并没有阻塞处理
                }
                lockCount.incrementAndGet();
                return true;
            }
        }

        try {
            // 首先尝试着去加锁
            boolean locked = tryLock();
            if (locked) {
                return true;
            }
            // 如果加锁失败就去等待
            while (!locked) {
                this.await();
                // 获取等待的子节点列表
                List<String> waiters = getWaiters();
                // 判断，是否加锁成功
                if (checkLocked(waiters)) {
                    locked = true;
                }
            }
            return true;
        } catch (Exception e) {
            lockCount.decrementAndGet(); // 减少计数
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlock() {
        // 只有加锁的线程，能够解锁
        if (!thread.equals(Thread.currentThread())) {
            return false;
        }
        // 减少可重入的计数
        int newLockCount = lockCount.decrementAndGet();
        // 计数不能小于 0
        if (newLockCount < 0) {
            throw new IllegalMonitorStateException("Lock count has gone negative for lock: " + locked_path);
        }
        // 如果计数不为 0，直接返回
        if (newLockCount != 0) {
            return true;
        }
        // 删除临时节点
        try {
            if (NodeOp.instance.isNodeExist(locked_path)) {
                client.delete().forPath(locked_path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean tryLock() throws Exception {
        // 创建临时 node
        locked_path = NodeOp.instance.createEphemeralSeqNode(LOCK_PREFIX);
        // 然后获取所有节点
        List<String> waiters = getWaiters();
        if (null == locked_path) {
            throw new Exception("zk error");
        }
        // 取得加锁的排队编号
        locked_short_path = getShortPath(locked_path);
        // 获取等待的子节点列表，判断自己是否第一个
        if (checkLocked(waiters)) {
            return true;
        }
        // 判断自己排第几个
        int index = Collections.binarySearch(waiters, locked_short_path);
        if (index < 0) { // 网络抖动，获取到的子节点列表里可能已经没有自己了
            throw new Exception("节点没有找到: " + locked_short_path);
        }
        // 如果自己没有获得锁，则要监听前一个节点
        prior_path = LOCK_PREFIX + waiters.get(index - 1);
        return false;
    }

    /*** 从 ZK 中拿到所有等待节点 */
    protected List<String> getWaiters() {
        try {
            List<String> list = client.getChildren().forPath(ZK_PATH);
            return list.stream()
                    .map(this::getShortPath)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    private void await() throws Exception {
        if (null == prior_path) {
            throw new Exception("prior_path error");
        }
        final CountDownLatch latch = new CountDownLatch(1);
        // 订阅比自己次小顺序节点的删除事件
        Watcher w = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
                log.info("[WatchedEvent] 节点删除");
                latch.countDown();
            }
        };
        client.getData().usingWatcher(w).forPath(prior_path);
        latch.await(WAIT_TIME, TimeUnit.SECONDS);
    }

    private boolean checkLocked(List<String> waiters) {
        // 节点按照编号，升序排列
        Collections.sort(waiters);
        // 如果是第一个，代表自己已经获得了锁
        if (locked_short_path.equals(waiters.get(0))) {
            log.info("成功的获取分布式锁,节点为 {}", locked_short_path);
            return true;
        }
        return false;
    }

    private String getShortPath(String locked_path) {
        int index = locked_path.length() - 10;
        return locked_path.substring(index);
    }

}
