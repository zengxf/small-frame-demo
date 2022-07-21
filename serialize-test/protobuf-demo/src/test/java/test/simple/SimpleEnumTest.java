package test.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/21.
 */
@Slf4j
public class SimpleEnumTest {

    @Test
    public void test() {
        Stream.of(SimpleEnum.Type.values())
                .peek(type -> log.info("-----------\ntype: [{}]", type))
                .forEach(type -> {
                    if (type != SimpleEnum.Type.UNRECOGNIZED)
                        log.info("t.name: [{}], t.value: [{}]", type.name(), type.getNumber());
                });
    }

}