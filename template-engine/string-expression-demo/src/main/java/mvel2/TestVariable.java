package mvel2;

import common_model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 支持 Null-Safe
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
@Slf4j
public class TestVariable extends Mvel2Utils {

    static Map<String, Object> context = Map.of(
            "$root", Map.of(
                    "global", Map.of(
                            "var1", 22,
                            "user", User.of("zxf", 22)
                    ),
                    "arr", List.of(22, 33, 44),
                    "user", User.of("zxf", 22)
            )
    );

    public static void main(String[] args) {
        String exp;

        exp = "$root.global.var1";
        log.info("[{}] => [{}] \n", exp, execute(exp, context));

        exp = "$root.global.user.name";
        log.info("[{}] => [{}] \n", exp, execute(exp, context));

        exp = "$root.arr[1]";
        log.info("[{}] => [{}] \n", exp, execute(exp, context));

        exp = "$root.?user.name";
        log.info("[{}] => [{}] \n", exp, execute(exp, context));

        exp = "$root.user.age > 33";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.?user2.age > 33";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.user.name";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.global.?user.?name";
        log.info("[{}] => [{}] \n", exp, execute(exp, context));

        exp = "$root.?global1.?user.?name";
        log.info("[{}] => [{}] \n", exp, execute(exp, context));
    }

}
