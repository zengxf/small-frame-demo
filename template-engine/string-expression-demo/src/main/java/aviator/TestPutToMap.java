package aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.JavaMethodReflectionFunctionMissing;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
@Slf4j
@Deprecated
public class TestPutToMap extends AviatorUtils {

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
        Object componentARes = execute(exp, context);
        log.info("[{}] => [{}]\n", exp, componentARes);

        exp = "$root.put(var2Key, var2)";
        log.info("[{}] => [{}]\n", exp, execute(exp, context));

        log.info("root: {}", root);
    }

}
