package test.illustration.math;

import test.illustration.util.Factorial;

public class SinX {

    static int n = 15;

    public static void main( String[] args ) {
        double v = Math.toRadians( 30D );
        System.out.println( v );
        System.out.println( Math.sin( v ) );
        System.out.println( sinX( v ) );
    }

    static double sinX( double x ) {
        double sum = x;
        for ( int i = 1; i <= n / 2; i++ ) {
            int sign = i % 2 == 0 ? 1 : -1;
            int v = i * 2 + 1;
            double xi = Math.pow( x, v );
            double xFactorial = Factorial.factorial( v );
            sum += ( sign * xi / xFactorial );
        }
        return sum;
    }

}
