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
public class CLHLock implements Lock {

    private final ThreadLocal<Node> preNode = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<Node> node = ThreadLocal.withInitial(Node::new);
    private final AtomicReference<Node> tail = new AtomicReference<>(new Node());

    private static class Node {
        private volatile boolean locked;
    }

    @Override
    public void lock() {
        final Node node = this.node.get();
        node.locked = true;
        Node pre = this.tail.getAndSet(node);
        this.preNode.set(pre);
        while (pre.locked) ;
    }

    @Override
    public void unlock() {
        final Node node = this.node.get();
        node.locked = false;
        this.node.set(this.preNode.get());
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
