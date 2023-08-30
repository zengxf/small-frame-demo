package test.script.js;

import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptEngineTest {

    @SuppressWarnings( "unchecked" )
    public static void main( String[] args ) throws Exception {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName( "javascript" ); // python or jython,

        // 向上下文中存入变量
        engine.put( "msg", "just a test" );
        // 定义类user
        String str = "msg += '!!!';var user = {name:'tom',age:23,ho:['football','basketball']}; ";
        engine.eval( str );

        // 从上下文引擎中取值
        String msg = (String) engine.get( "msg" );
        System.out.println( msg );

        // 定义数学函数
        engine.eval( "function add (a, b) {c = a + b; return c; }" );

        // 取得调用接口
        Invocable jsInvoke = (Invocable) engine;

        // 定义加法函数
        Object result1 = jsInvoke.invokeFunction( "add", new Object[] { 10, 5 } );
        System.out.println( result1 );

        engine.eval( "function run() {print('www.java2s.com');}" );
        Invocable invokeEngine = (Invocable) engine;
        Runnable runner = invokeEngine.getInterface( Runnable.class );
        // 定义线程运行之
        Thread t = new Thread( runner );
        t.start();
        t.join();

        // 导入其他java包
        String jsCode = "var list2 = ['A', 'B', 'C'];";
        engine.eval( jsCode );
        List<String> list2 = (List<String>) engine.get( "list2" );
        for ( String val : list2 ) {
            System.out.println( val );
        }
    }
}
