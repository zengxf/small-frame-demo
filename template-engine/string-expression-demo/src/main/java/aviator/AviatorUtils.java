package aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.JavaMethodReflectionFunctionMissing;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;

import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
public class AviatorUtils {

    public static Object execute(String expression, Map<String, Object> context) {
//        AviatorEvaluator.setFunctionMissing(JavaMethodReflectionFunctionMissing.getInstance());
        Expression exp = AviatorEvaluator.compile(expression);
        var res = exp.execute(context);
//        var res = AviatorEvaluator.execute(expression, context);
        return res;
    }

}
