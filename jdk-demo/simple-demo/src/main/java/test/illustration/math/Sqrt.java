package test.illustration.math;

/**
 * demo: 开平方根 <br>
 * <a href="https://mp.weixin.qq.com/s/ImRXkz0EjY8uThjzddRuTA">原文参考</a>
 * <p>
 * Created by zengxf on 2018-12-18
 */
public class Sqrt {
    public static void main( String[] args ) {
        System.out.println( Math.sqrt( 2 ) );
        System.out.println( sqrt( 2 ) );
        System.out.println( jsSqrt( 2 ) );
        System.out.println( 1e-3 ); // 0.1 ^ 3
        System.out.println( Math.pow( 0.1, 3 ) ); // 0.1 ^ 3
        System.out.println( Math.pow( 10, -3 ) ); // 0.1 ^ 3
    }

    public static double sqrt( double x ) {
        double eps = 1e-12;
        double t = x;
        while ( Math.abs( t - x / t ) > eps * t ) {
            t = ( t + x / t ) / 2.0;
        }
        return t;
    }

    public static double jsSqrt( double n ) {
        // 当 n >= 1 时，从 n 开始迭代
        // 当 n < 1 时，从 1 开始迭代
        double res = n >= 1 ? n : 1;
        while ( res * res - n > 1e-12 )
            res = 0.5 * ( res + n / res );
        return res;
    }
}
