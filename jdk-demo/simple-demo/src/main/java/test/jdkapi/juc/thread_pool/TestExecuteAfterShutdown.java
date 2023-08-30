package test.jdkapi.juc.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <br/>
 * Created by ZXFeng on 2022/3/3.
 */
@Slf4j
public class TestExecuteAfterShutdown {

    public static void main(String[] args) {
        ExecutorService ex = Executors.newFixedThreadPool(1);
        ex.execute(() -> log.info("---------00----"));
        ex.shutdown();                                  // 关闭
        ex.execute(() -> log.info("---------11----"));  // 拒绝执行异常
    }

}
