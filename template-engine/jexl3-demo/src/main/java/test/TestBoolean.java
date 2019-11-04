package test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

public class TestBoolean {

    public static Object convertToCode( String jexlExp, Map<String, Object> map ) {
        // 创建或检索引擎
        JexlEngine jexl = new Engine();
        // 创建一个表达式
        JexlExpression e = jexl.createExpression( jexlExp );
        // 创建上下文并添加数据
        JexlContext jc = new MapContext();
        for ( String key : map.keySet() ) {
            jc.set( key, map.get( key ) );
        }
        // 现在评估表达式，得到结果
        Object value = e.evaluate( jc );
        return value;
    }

    public static void main( String... args ) throws Exception {
        try {
            Map<String, Object> map = new HashMap<String, Object>( 16 );
            map.put( "money", 6100 );
            String expression = "money >= 2000 && money <= 4000";
            Object code = convertToCode( expression, map );
            System.out.println( (Boolean) code );
            map.put( "money", 2520 );
            code = convertToCode( expression, map );
            System.out.println( (Boolean) code );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
