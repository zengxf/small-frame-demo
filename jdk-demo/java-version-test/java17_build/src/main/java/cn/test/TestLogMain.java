package cn.test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p/>
 * Created by ZXFeng on 2024/10/9
 */
@Slf4j
public class TestLogMain {

    public static void main(String[] args) {
        log.info("测试 info");
        log.warn("测试 warn");
        log.error("测试 error");
    }

}
