package test.biz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DataUtilsTest {

    @Test
    public void mockUsers() {
        DataUtils.mockUsers(2)
                .forEach(userPO -> log.info("{}", userPO));
    }

}