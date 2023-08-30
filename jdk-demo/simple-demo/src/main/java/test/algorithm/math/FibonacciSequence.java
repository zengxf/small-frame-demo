package test.algorithm.math;

/*** 斐波那契数列 */
public class FibonacciSequence {

    public static void main( String[] args ) {
        // 1 1 2 3 5 8 13 
        System.out.println( fibonacci( 23 ) );
        System.out.println( fibonacci( 24 ) );
    }

    static int fibonacci( int a ) {
        if ( a == 0 )
            return 0;
        if ( a == 1 )
            return 1;
        return fibonacci( a - 1 ) + fibonacci( a - 2 );
    }

}
