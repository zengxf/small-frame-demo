package test.jdkapi.reflex.compiler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 测试 Java 编译器程序接口
 * 
 * <p>
 * Created by zengxf on 2018-04-11
 */
public class TestCompilerByString {

    public static void main( String[] args ) throws IOException, URISyntaxException {
        String java = "class Test{ public static void test() { System.out.println(1234); } }";
        System.out.println( java );

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager( diagnosticCollector, Locale.ENGLISH, Charset.forName( "utf-8" ) );
        SimpleJavaFileObject javaSourceObject = new SimpleJavaFileObject( new URI( "Test" ), Kind.SOURCE ) {
            public CharSequence getCharContent( boolean ignoreEncodingErrors ) throws IOException {
                return java;
            }
        };
        Iterable<? extends JavaFileObject> javaFileObjects = Arrays.asList( javaSourceObject );

        CompilationTask task = compiler.getTask( null, fileManager, diagnosticCollector, null, null, javaFileObjects );
        Boolean result = task.call();

        System.out.println( "result: " + result );
        if ( result ) {
        } else {
            List<?> list = diagnosticCollector.getDiagnostics();
            for ( Object object : list ) {
                Diagnostic<?> d = (Diagnostic<?>) object;
                System.out.println( d.getMessage( Locale.ENGLISH ) );
            }
        }
    }

}
