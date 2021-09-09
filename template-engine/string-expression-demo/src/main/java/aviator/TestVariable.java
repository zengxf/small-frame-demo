package aviator;

import common_model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
@Slf4j
public class TestVariable extends AviatorUtils {

    static Map<String, Object> context = Map.of(
            "$root", Map.of(
                    "componentA", Map.of(
                            "$isSuccess", true,
                            "out", Map.of(
                                    "resInt", 21,
                                    "resStr", "OK"
                            )
                    ),
                    "user", User.of("zxf", 22),
                    "arr", List.of(12, 23, 32)
            )
    );

    public static void main(String[] args) {
        String exp;

        exp = "$root.componentA";
        Object componentARes = execute(exp, context);
        log.info("[{}] => [{}]\n", exp, componentARes);

        exp = "$root.componentA.$isSuccess";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.componentA.out.resInt";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.componentA.out.resStr";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.arr[1]";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.user.age > 33";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

//        exp = "$root.user2.age > 33";
//        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        exp = "$root.user.name";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

//        exp = "$root.user2.name";
//        log.info("[{}] => [{}]\n", exp, execute(exp, context));
    }

}
