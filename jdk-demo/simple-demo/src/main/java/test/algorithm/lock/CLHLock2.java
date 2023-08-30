package test.algorithm.lock;

import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 参考：疯狂创客圈
 * <br/>
 * Created by ZXFeng on 2022/6/16.
 */
public class CLHLock2 implements Lock {

    private static ThreadLocal<Node> curNodeLocal = new ThreadLocal();
    private String name;
    private AtomicReference<Node> tail = new AtomicReference<>(null);

    public CLHLock2() {
        this.name = "CLHLock2-def";
        tail.getAndSet(Node.EMPTY);
    }

    @Override
    public void lock() {
        Node curNode = new Node(true, null);
        Node preNode = tail.get();
        while (!tail.compareAndSet(preNode, curNode)) {
            preNode = tail.get();
        }
        curNode.setPrevNode(preNode);

        while (curNode.getPrevNode().isLocked()) {
            Thread.yield();
        }
        curNodeLocal.set(curNode);
    }

    @Override
    public void unlock() {
        Node curNode = curNodeLocal.get();
        curNode.setPrevNode(null); // help for GC
        curNodeLocal.set(null);
        curNode.setLocked(false);
    }

    @Data
    static class Node {
        public Node(boolean locked, Node prevNode) {
            this.locked = locked;
            this.prevNode = prevNode;
        }

        volatile boolean locked;
        Node prevNode;
        public static final Node EMPTY = new Node(false, null);
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

    @Override
    public String toString() {
        return "CLHLock{" + name + '}';
    }

}