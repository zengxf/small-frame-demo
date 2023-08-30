package test.script.js;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ValueConvertTest {
    public static void main( String[] args ) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName( "js" );
        System.out.println( engine );
        // 定义类user
        String str = "var obj = { value: 1, key: 'zxf' };  obj;";
        Bindings msg = (Bindings) engine.eval( str );
        System.out.println( msg.get( "value" ) );
        System.out.println( msg.get( "key" ) );
        System.out.println( msg.getClass() );
        System.out.println( msg );
    }
}
