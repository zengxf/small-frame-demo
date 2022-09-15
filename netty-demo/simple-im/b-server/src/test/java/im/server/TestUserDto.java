package im.server;

import im.common.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Slf4j
public class TestUserDto {

    @Test
    public void test() {
        UserDto dto = UserDto.of("zxf", 88);
        log.info("dto: [{}]", dto);
    }

}
