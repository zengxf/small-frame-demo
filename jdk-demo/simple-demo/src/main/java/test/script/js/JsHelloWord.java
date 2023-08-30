package test.script.js;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JsHelloWord {
    public static void main( String[] args ) throws ScriptException, FileNotFoundException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName( "nashorn" );
        System.out.println( engine.getClass() );
        engine.eval( "print('Hello World!')" );

        engine.eval( new FileReader( JsHelloWord.class.getResource( "js-folder/script.js" )
                .getPath() ) );

        // 对于简单的单行函数，我们可以去掉花括号
        engine.eval( "function sqr(x) x * x; print(sqr(3));" );

        engine.eval( "print(__FILE__, __LINE__, __DIR__);" );
    }
}
