package test.jdkapi.ju.function;

import java.util.function.Function;

/**
 * compose() 前处理，andThen() 后处理
 * 
 * <p>
 * Created by zengxf on 2018-03-08
 */
public class TestFunction {

    public static void main( String[] args ) {
        Function<String, String> f1 = s -> "-a1-" + s;
        Function<String, String> f2 = s -> "-a2-" + s;
        Function<String, String> f3 = s -> "-a3-" + s;
        String v = f1.compose( f2 ).andThen( f3 ).apply( "zxf" );
        System.out.println( v );
    }

}
