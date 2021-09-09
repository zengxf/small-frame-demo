package mvel2;

import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;

import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
public class Mvel2Utils {

    public static Object execute(String expression, Map<String, Object> context) {
        ExpressionCompiler compiler = new ExpressionCompiler(expression.trim());
        CompiledExpression compile = compiler.compile();
        var res = MVEL.executeExpression(compile, context);
        return res;
    }

}
