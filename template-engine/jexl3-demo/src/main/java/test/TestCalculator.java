package test;

import java.util.List;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

public class TestCalculator {

    public static void main( String[] args ) {
        List<String> list = List.of( "12 + 56 * 89", "88 / 55", "java.lang.Math.log(10)" );
        JexlEngine jexl = new Engine();
        JexlContext jc = new MapContext();
        list.forEach( ex -> {
            try {
                System.out.println( ex );
                JexlExpression e = jexl.createExpression( ex );
                Object value = e.evaluate( jc );
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
