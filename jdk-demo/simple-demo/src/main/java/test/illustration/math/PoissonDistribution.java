package test.illustration.math;

/**
 * demo: 泊松分布
 * <p>
 * Created by zengxf on 2018-11-22
 */
public class PoissonDistribution {

    public static void main( String[] args ) {
        int k = 8;
        double lambda = 0.5D;
        double p = poissonDistribution( k, lambda );
        System.out.println( String.format( "%.8f", p ) );
    }

    /*** 泊松分布计算 */
    static double poissonDistribution( int k, double lambda ) {
        double lambda_k = Math.pow( lambda, k );
        double e_neg_lambda = Math.pow( Math.E, -lambda );
        double numerator = lambda_k * e_neg_lambda;
        int factorial = factorial( k );
        double p = numerator / factorial;
        return p;
    }

    /*** 阶乘 */
    static int factorial( int k ) {
        if ( k == 0 )
            return 1;
        return k * factorial( k - 1 );
    }

}
