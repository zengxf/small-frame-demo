package test.jdkapi.math;

import java.math.BigDecimal;

public class TestPrecision {
    public static void main( String[] args ) throws Exception {
        System.out.println( 2.0 - 1.9 );
        BigDecimal v = BigDecimal.valueOf( 2.0 ).subtract( BigDecimal.valueOf( 1.9 ) );
        System.out.println( v.doubleValue() );
        System.out.println( BigDecimal.valueOf( 20000.2 ) );
        System.out.println( new BigDecimal( 20000.2 ) );
    }
}
