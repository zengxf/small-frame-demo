package test.jdkapi.ju;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * reverse() 倒转集合
 * 
 * <p>
 * Created by zengxf on 2018-02-26
 */
public class TestCollections {
    public static void main( String[] args ) {
        test_reverse();
    }

    static void test_reverse() {
        List<Integer> list = IntStream.of( 1, 2, 3, 4 ).boxed().collect( Collectors.toList() );
        System.out.println( list );
        Collections.reverse( list ); // 倒转
        System.out.println( list );
    }
}
