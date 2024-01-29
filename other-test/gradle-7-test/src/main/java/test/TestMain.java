package test;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p/>
 * Created by ZXFeng on 2024/1/29
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) {
        log.info("Test OK");

        String msg = StrUtil.format("test: [{}]", 123);
        log.info(msg);
    }

}
