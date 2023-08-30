package test.jdkapi.ju.function;

import java.util.function.IntPredicate;

/**
 * and() or() negate() 连接
 * 
 * <p>
 * Created by zengxf on 2018-03-08
 */
public class TestPredicate {

    public static void main( String[] args ) {
        IntPredicate p1 = i -> i < 9;
        IntPredicate p2 = i -> i > 2;
        IntPredicate p3 = i -> i == 4;
        IntPredicate p4 = i -> i == 5;
        boolean v = p1.and( p2 ).or( p3 ).and( p4.negate() ).test( 3 );
        System.out.println( v );
    }

}
