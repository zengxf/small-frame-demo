package test.common;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * 简单任务
 * <p/>
 * Created by ZXFeng on 2026/1/28
 */
@Slf4j
public class SimpleTask implements Constant, Callable<String>, Serializable { // 需要实现 Serializable

    @Override
    public String call() {
        String msg = "Hello from -------------> " + System.getProperty(INSTANCE_CFG_KEY);
        log.info(msg); // 这个日志会在服务端输出
        return msg;
    }

}