package test.illustration.util;

/**
 * 阶乘，int 极限 19，long 极限 25
 * <p>
 * Created by zengxf on 2018-12-18
 */
public class Factorial {

    public static final int MAX_INT = 19, MAX_LONG = 25;

    public static int factorial( int v ) {
        if ( v < 0 )
            throw new ArithmeticException( "算法异常（阶乘不能小于 0），传入值：" + v );
        if ( v > MAX_INT )
            throw new ArithmeticException( "算法异常（超出极限），传入值：" + v );
        if ( v == 0 )
            return 1;
        return factorial( v - 1 ) * v;
    }

    public static long factorial( long v ) {
        if ( v < 0 )
            throw new ArithmeticException( "算法异常（阶乘不能小于 0），传入值：" + v );
        if ( v > MAX_LONG )
            throw new ArithmeticException( "算法异常（超出极限），传入值：" + v );
        if ( v == 0 )
            return 1;
        return factorial( v - 1 ) * v;
    }

    public static void main( String[] args ) {
        System.out.println( factorial( 0 ) );
        System.out.println( factorial( 1 ) );
        System.out.println( factorial( 2 ) );
        System.out.println( factorial( 3 ) );
        System.out.println( factorial( 19 ) );
        System.out.println( factorial( 25L ) );
    }

}
