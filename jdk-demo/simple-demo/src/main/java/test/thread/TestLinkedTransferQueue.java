package test.thread;

import java.util.concurrent.LinkedTransferQueue;

public class TestLinkedTransferQueue {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        new Thread(() -> {
            System.out.println("## - 1 - " + queue.poll());
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("## - 2 - " + queue.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        queue.offer("b");
        System.out.println("-- 00");
        queue.transfer("d");
        System.out.println("-- 0");
        queue.transfer("e");
        System.out.println("-- 1");
    }
}
