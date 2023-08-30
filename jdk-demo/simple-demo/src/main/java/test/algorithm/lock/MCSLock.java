package test.algorithm.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ref https://www.cnblogs.com/sanzao/p/10567529.html
 * <br/>
 * Created by ZXFeng on 2022/6/14.
 */
public class MCSLock implements Lock {

    private final ThreadLocal<Node> node = ThreadLocal.withInitial(Node::new);
    private final AtomicReference<Node> tail = new AtomicReference<>();

    private static class Node {
        private volatile boolean locked = false;
        private volatile Node next = null;
    }

    @Override
    public void lock() {
        Node node = this.node.get();
        node.locked = true;
        Node pre = tail.getAndSet(node);
        if (pre != null) {
            pre.next = node;
            while (node.locked) ;
        }
    }

    @Override
    public void unlock() {
        Node node = this.node.get();
        if (node.next == null) {
            if (tail.compareAndSet(node, null)) {
                return;
            }
            // while (node.next == null) ;
        }
        node.next.locked = false;
        node.next = null;
    }

    @Override
    public void lockInterruptibly() {
        throw new RuntimeException("todo");
    }

    @Override
    public boolean tryLock() {
        throw new RuntimeException("todo");
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        throw new RuntimeException("todo");
    }

    @Override
    public Condition newCondition() {
        throw new RuntimeException("todo");
    }

}
