package test.biz;

import com.nimbusds.jwt.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class DataWriterTest {

    @Test
    public void outJson() {
        log.info("----------------\n\n{}\n\n----------------", DataWriter.SCHEMA_JSON);
    }

    @Test
    public void writeToParquet() {
        List<UserPO> data = DataUtils.mockUsers(100);
        DataWriter.writeToParquet(data);
    }

}