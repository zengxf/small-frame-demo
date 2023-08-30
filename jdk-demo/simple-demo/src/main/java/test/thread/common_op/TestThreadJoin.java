package test.thread.common_op;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zengxf
 */
@Slf4j
public class TestThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        log.info("main start");

        testNotifyAllOnThreadEnd();
        // threadJoinSelf();

        log.info("main end");
    }

    /*** 当前线程会一直等待，出现假死现象，不会报错 */
    static void threadJoinSelf() throws InterruptedException {
        Thread a = new Thread(() -> {
            log.info("--------- start");
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                log.error("Join error!", e);
            }
            log.info("a --------- end");
        }, "test-a");

        a.start();

        a.join();
    }

    /*** 线程执行完会调用：notifyAll() */
    static void testNotifyAllOnThreadEnd() throws InterruptedException {
        Thread a = new Thread(() -> {
            log.info("a start");
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("a end");
        }, "test-aa");

        Thread b = new Thread(() -> {
            synchronized (a) {
                try {
                    log.info("b start");
                    a.wait();
                    log.info("b end");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "test-bb");

        a.start();
        b.start();

        a.join();
    }

}
