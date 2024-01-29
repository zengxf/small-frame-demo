package test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class NumUtilsTest {

    @Test
    void sum() {
        int sum = NumUtils.sum(1, 2, 3, 4);
        log.info("sum: [{}]", sum);
        assertEquals(10, sum, "计算结果不对");
    }

}