package test.script.js;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JsFunTest {
    static Invocable invocable;

    public static void main( String[] args ) throws FileNotFoundException, ScriptException, NoSuchMethodException, UnsupportedEncodingException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName( "nashorn" );
        System.out.println( engine.getClass() );

        String path = JsFunTest.class.getResource( "js-folder/fun.js" )
                .getPath();
        // FileReader reader = new FileReader( path );
        InputStreamReader reader = new InputStreamReader( new FileInputStream( path ), "UTF-8" );
        engine.eval( reader );

        invocable = (Invocable) engine;

        Object result = test( "fun1", "Peter Parker" );
        System.out.println( result );
        System.out.println( result.getClass() );

        test( "fun2", new Date() );
        test( "fun2", LocalDateTime.now() );
        test( "fun2", new Person() );

        test( "fun3" );

        test( "fun4" );

        test( "testJavaArray" );

        test( "testJavaCollection" );

        test( "testJavaMap" );

        test( "testLambda" );

        test( "testExtend" );

        test( "testMethod" );

        test( "testImport" );

        test( "testTransform" );

        test( "testSuper" );

        test( "testLoad" );
    }

    protected void run() {
        System.out.println( "my test run ...." );
    }

    static Object test( String methodName, Object... args ) throws NoSuchMethodException, ScriptException {
        System.out.println();
        System.out.println( "-----\t\t" + methodName + "\t------" );
        return invocable.invokeFunction( methodName, args );
    }

    static class Person {
    }

    // public !!!
    public static String fun1( String name ) {
        System.out.format( "Hi there from Java, %s %n", name );
        return "greetings from java";
    }

    // public !!!
    public static void fun2( Object obj ) {
        System.out.format( "Hi there from Java, %s %n", obj.getClass() );
    }

    public static void fun3( Bindings mirror ) {
        System.out.println( mirror.getClass() );
        System.out.println( mirror.keySet() );
    }

    public static void fun4( Bindings mirror ) {
        System.out.println( mirror.keySet() );
        System.out.println( mirror.get( "getFullName" )
                .getClass() );
    }
}
