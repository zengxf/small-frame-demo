package test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <p/>
 * Created by ZXFeng on 2024/1/29
 */
@Slf4j
public class JUnit5Test {

    @Test
    @DisplayName("JUnit-5 测试")
    public void test() {
        log.info("JUnit-5 test OK!");
    }

}
