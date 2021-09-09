package aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
@Slf4j
public class SimpleTest {

    public static void main(String[] args) {
        String exp;

        exp = "21 + 32";
        Expression expression = AviatorEvaluator.compile(exp);
        var res = expression.execute();

        log.info("[{}] => [{}]", exp, res);
    }

}
