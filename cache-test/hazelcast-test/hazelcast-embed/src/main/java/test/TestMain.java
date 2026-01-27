package test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p/>
 * Created by ZXFeng on 2023/11/22
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) {
        log.debug("Hello world!");
        log.info("Hello world!");
        log.warn("Hello world!");
        log.error("Hello world!");
        log.error("Hello world!", new RuntimeException("Test err 666"));
        log.info("Hello world!");
    }

}
