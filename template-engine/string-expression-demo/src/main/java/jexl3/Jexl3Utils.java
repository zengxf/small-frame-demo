package jexl3;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Map;

/**
 * <br/>
 * Created by ZXFeng on  2021/9/9.
 */
public class Jexl3Utils {

    public static Object execute(String expression, Map<String, Object> context) {
        JexlEngine jexl = new Engine();
        JexlExpression e = jexl.createExpression(expression);  // 创建一个表达式
        JexlContext jc = new MapContext();  // 创建上下文并添加数据
        context.forEach(jc::set);
        Object value = e.evaluate(jc);
        return value;
    }

}
