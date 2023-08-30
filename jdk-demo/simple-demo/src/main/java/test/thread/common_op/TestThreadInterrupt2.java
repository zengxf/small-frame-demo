package test.thread.common_op;

import lombok.extern.slf4j.Slf4j;
import util.SleepUtils;

/**
 * <pre>
 * - 如果线程的 `interrupt` 方法先被调用
 *   - 然后线程开始调用阻塞方法进入阻塞状态
 *     - `InterruptedException` 异常依旧会抛出
 *   - 如果线程捕获 `InterruptedException` 异常后
 *     - 继续调用阻塞方法，将不再触发 `InterruptedException` 异常
 * </pre>
 * Created by ZXFeng on 2022/5/13.
 */
@Slf4j
public class TestThreadInterrupt2 {

    public static void main(String[] args) {
        Runnable run = () -> {
            log.info("run 1 ----");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                log.error("第 1 次中断！");
            }
            log.info("run 2 ----");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                log.error("第 2 次中断！");
            }
            log.info("run 3 ----");
        };
        Thread thread = new Thread(run, "interrupt-test-thread");
        thread.start();
        log.info("--- 1. interrupt()");
        thread.interrupt();
        SleepUtils.millisecond(500L);
        thread.interrupt();
        log.info("--- 2. interrupt()");
    }

}
