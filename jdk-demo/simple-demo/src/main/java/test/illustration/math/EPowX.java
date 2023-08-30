package test.illustration.math;

import test.illustration.util.Factorial;

/**
 * demo: e^x（不准确，参考：泰勒公式）
 * <p>
 * Created by zengxf on 2018-12-18
 */
public class EPowX {

    static int n = 15;

    public static void main( String[] args ) {
        int x = 5;
        System.out.println( Math.pow( Math.E, x ) );
        System.out.println( ePowX( x ) );
    }

    static double ePowX( int x ) {
        double sum = 1 + x;
        for ( int i = 2; i <= n; i++ ) {
            double xi = Math.pow( x, i );
            double xFactorial = Factorial.factorial( i );
            sum += ( xi / xFactorial );
        }
        return sum;
    }

}
