package test.jdkapi.reflex.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * 测试 Java 编译器程序接口
 * 
 * <p>
 * Created by zengxf on 2018-04-11
 */
public class TestCompilerByFile {

    public static void main( String[] args ) throws IOException {
        String java = "public class Test{ public static void test() { System.out.println(1234); } }";
        System.out.println( java );

        String path = TestCompilerByFile.class.getResource( "Test.java.txt" )
                .getPath();
        Path srcPath = Paths.get( path.replaceFirst( "/", "" ) );
        Path targetPath = srcPath.resolve( "../Test.java" )
                .normalize();
        Files.deleteIfExists( targetPath );
        Files.copy( srcPath, targetPath );
        System.out.println( srcPath );
        System.out.println( targetPath );

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run( System.in, System.out, System.err, targetPath.toString() );
        if ( result == 0 ) {
            System.out.println( "success" );
        } else {
            System.out.println( "fail" );
        }
    }

}
