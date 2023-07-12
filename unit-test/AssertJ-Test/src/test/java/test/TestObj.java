package test;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * <br>
 * Created by ZXFeng on 2023/7/12
 */
@Slf4j
public class TestObj {

    @Test
    public void testObj() {
        Object obj = "xx";
        Assertions.assertThat(obj)
                .as("对象不能为空")
                .isNotNull();

        Object obj2 = null;
        Assertions.assertThat(obj2)
                .as("对象必须为空")
                .isNull();
    }

}
