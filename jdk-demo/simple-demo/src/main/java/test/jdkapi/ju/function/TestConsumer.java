package test.jdkapi.ju.function;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * andThen() 添加后处理者
 * 
 * <p>
 * Created by zengxf on 2018-03-08
 */
public class TestConsumer {

    public static void main( String[] args ) {
        IntConsumer c1 = i -> {
            System.out.println( "i - 1 - " + i );
        };
        IntConsumer c2 = i -> {
            System.out.println( "i - 2 - " + i );
        };
        IntStream.range( 1, 3 ).forEach( c1.andThen( c2 ) );
    }

}
