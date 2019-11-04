package test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.udojava.evalex.AbstractFunction;
import com.udojava.evalex.Expression;

public class TestCalculator {

    public static void main( String[] args ) {
        List<String> list = List.of( //
                "12 + 56 * 89", //
                "88.0 / 55", //
                "23 / 3", //
                "5 ^ 3", //
                "log10(1000)", //
                "lg(2, 8)", //
                "lg(1.1, 2)", //
                "log(10)" //
        );
        list.forEach( ex -> {
            try {
                System.out.print( ex );
                Expression expression = new Expression( ex );
                expression.addFunction( new AbstractFunction( "lg", 2 ) {
                    @Override
                    public BigDecimal eval( List<BigDecimal> parameters ) {
                        double v1 = parameters.get( 0 )
                                .doubleValue();
                        double v2 = parameters.get( 1 )
                                .doubleValue();
                        return new BigDecimal( Math.log( v2 ) / Math.log( v1 ) ).setScale( 2, RoundingMode.HALF_UP );
                    }
                } );
                expression.setPrecision( 3 );
                BigDecimal result = expression.eval();
                System.out.println( " = " + result );
                System.out.println();
            } catch ( Exception e ) {
                // e.printStackTrace();
                System.err.println( e.getMessage() );
                System.out.println();
            }
        } );
        System.out.println( "Math.log( 10 ) = " + Math.log( 10 ) );
        System.out.println( "Math.log( 2 ) / Math.log(1.1) = " + Math.log( 2 ) / Math.log( 1.1 ) );
    }

}
