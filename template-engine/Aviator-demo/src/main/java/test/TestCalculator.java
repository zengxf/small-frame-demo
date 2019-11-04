package test;

import java.util.List;

import com.googlecode.aviator.AviatorEvaluator;

public class TestCalculator {

    public static void main( String[] args ) {
        List<String> list = List.of( "12 + 56 * 89", "88.0 / 55", "log(10)" );
        list.forEach( ex -> {
            try {
                System.out.println( ex );
                Object value = AviatorEvaluator.execute( ex );
                System.out.println( value.getClass() + "\t" + value );
                System.out.println();
            } catch ( Exception e ) {
                System.err.println( e.getMessage() );
                System.out.println();
            }
        } );
        System.out.println( java.lang.Math.log( 10 ) );
    }

}
