package zxftest.logback1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMain {

    public static void main(String[] args) {
        log.debug("test debug - 中文");
        log.info("test info - 中文");
        log.warn("test warn - 中文");
        log.error("test error - 中文");
        System.out.println();
        LogTest2.testLog();
    }

}
