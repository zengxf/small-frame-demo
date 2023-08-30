package test.jvm.oom;

/**
 * -Xss2048
 * 
 * <p>
 * Created by zengxf on 2017-11-24
 */
public class StackOverflowExample {
    public static void main( String args[] ) {
        StackOverflowExample sfe = new StackOverflowExample();
        int num = 10;
        System.out.println( "Factorial of " + num + " is " + sfe.computeFactorial( 10 ) );
    }

    private int computeFactorial( int num ) {
        System.out.println( num );
        if ( num == 1 ) {
            return 1;
        }
        return computeFactorial( num + 1 ) * num;
    }
}
