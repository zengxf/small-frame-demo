package test.script.js;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ChineseUpTest {
    public static void main( String[] args ) throws UnsupportedEncodingException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName( "js" );
        InputStream is = JsFunTest.class.getResourceAsStream( "js-folder/Chinese-Up.js" );
        InputStreamReader reader = new InputStreamReader( is, "UTF-8" );
        engine.eval( reader );
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction( "numberToChinese", 3223.32D );
        System.out.println( result );
        result = invocable.invokeFunction( "numberToChinese", 0.0D );
        System.out.println( result );
    }
}
