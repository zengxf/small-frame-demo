package zxftest.logback1;

import lombok.extern.slf4j.Slf4j;

/**
 * <p/>
 * Created by ZXFeng on 2024/9/11
 */
@Slf4j(topic = "test")
public class LogTest2 {

    public static void testLog() {
        log.debug("-------------> debug");
        log.info("-------------> info");
        log.warn("-------------> warn");
        log.error("-------------> error");
    }

}
