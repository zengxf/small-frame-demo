package test.thread;

import util.SleepUtils;

import java.time.LocalTime;

/**
 * <br/>
 * Created by ZXFeng on 2022/6/2.
 */
public class TestWaitNotify {

    public static void main(String[] args) {
        Object lock = new Object();
        Runnable r1 = () -> {
            System.out.println("wait-thread entry!");
            synchronized (lock) {
                System.out.println("1. time: " + LocalTime.now());
                try {
                    lock.wait(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2. time: " + LocalTime.now());
            }
        };
        Runnable r2 = () -> {
            System.out.println("notify-thread entry!");
            SleepUtils.millisecond(500L);
            synchronized (lock) {
                lock.notify();
                System.out.println("notify!");
            }
        };

        new Thread(r1, "wait-thread").start();
        new Thread(r2, "notify-thread").start();
    }

}
