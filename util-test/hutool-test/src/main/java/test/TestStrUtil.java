package test;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on 2021/12/28.
 */
@Slf4j
public class TestStrUtil {

    public static void main(String[] args) {
        String fmt = "{name} != null && {name} >= {value}";
        Map<String, Object> map = Map.of("name", "age", "value", 33);
        String str = StrUtil.format(fmt, map);
        log.info("fmt: [{}], map: [{}]", fmt, map);
        log.info("str: [{}]", str);
    }

}
