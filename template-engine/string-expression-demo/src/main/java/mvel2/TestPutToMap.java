package mvel2;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试将值 put 到 Map 里去
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
@Slf4j
public class TestPutToMap extends Mvel2Utils {

    public static void main(String[] args) {
        Map<String, Object> root = new HashMap<>();
        Map<String, Object> context = Map.of(
                "$root", root,
                "var1Key", "v1",
                "var1", 22,
                "var2Key", "v2",
                "var2", "OK"
        );

        String exp;

        exp = "$root.put(var1Key, var1)";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.put(var2Key, var2)";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        log.info("root: {}", root);
    }

}
