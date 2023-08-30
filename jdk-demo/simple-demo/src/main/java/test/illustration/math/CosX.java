package test.illustration.math;

import test.illustration.util.Factorial;

public class CosX {

    static int n = 19;

    public static void main( String[] args ) {
        double v = Math.toRadians( 60D );
        System.out.println( v );
        System.out.println( Math.cos( v ) );
        System.out.println( cosX( v ) );
    }

    static double cosX( double x ) {
        double sum = 1;
        for ( int i = 1; i <= n / 2; i++ ) {
            int sign = i % 2 == 0 ? 1 : -1;
            int v = i * 2;
            double xi = Math.pow( x, v );
            double xFactorial = Factorial.factorial( v );
            sum += ( sign * xi / xFactorial );
        }
        return sum;
    }

}
