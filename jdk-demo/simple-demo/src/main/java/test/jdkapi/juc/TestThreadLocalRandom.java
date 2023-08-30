package test.jdkapi.juc;

import java.util.concurrent.ThreadLocalRandom;

public class TestThreadLocalRandom {

    public static void main(String[] args) {
        testError();
        // testCommon();
    }

    static void testCommon() {
        for (int i = 0; i < 10; i++)
            new Thread(() -> {
                System.out.println(ThreadLocalRandom.current()
                        .nextInt()); // 直接用到内里来获取
            }).start();
    }

    // ThreadLocalRandom.current() 会使用初始化它的线程来填充随机种子，这会带来导致多个线程使用相同的 seed
    static void testError() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++)
            new Thread(() -> {
                System.out.println(threadLocalRandom.nextInt());
            }).start();
    }

}
