package test.jdkapi.lang;

import java.math.BigDecimal;

/**
 * Created by zengxf on 2021/4/7.
 */
public class TestBigDecimal {

    public static void main( String[] arr ) {
        BigDecimal num = new BigDecimal( "0.33" ).setScale( 2 );
        System.out.println( num );
    }

}
