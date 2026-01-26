package test.biz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class DataReaderTest {

    @Test
    public void readFromParquet() {
        List<UserPO> data = DataReader.readFromParquet();
        data.forEach(userPO -> log.info("{}", userPO));
    }

}