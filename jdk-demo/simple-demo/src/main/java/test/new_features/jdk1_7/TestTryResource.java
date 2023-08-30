package test.new_features.jdk1_7;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * 看反编译后的语法——用 IEDA 反编译
 * 
 * <p>
 * Created by zxf on 2017-07-28
 */
// M:\zxf-demo-github\zxf_super_demo\simple-demo\bin\main\test\new_features\jdk1_7
public class TestTryResource {
    public static void main( String[] args ) {
        try ( MyAutoClose my = new MyAutoClose() ) {
            my.test();
        } catch ( IOException e ) {
            System.out.println( "io exception" );
        }
    }

    static String test() throws IOException {
        Reader inputString = new StringReader( "ddd" );
        try ( BufferedReader br = new BufferedReader( inputString ) ) {
            return parse( br );
        }
    }

    static String parse( BufferedReader br ) throws IOException {
        return br.readLine();
    }

}

class MyAutoClose implements Closeable {

    @Override
    public void close() throws IOException {
        System.out.println( "my auto close" );
    }

    public void test() throws IOException {
        System.out.println( "test" );
    }

}