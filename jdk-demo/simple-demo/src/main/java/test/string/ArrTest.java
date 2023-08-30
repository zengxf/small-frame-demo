package test.string;

import java.util.Arrays;

public class ArrTest {
    public static void main( String[] args ) {

        String str = "=D6=D6=D6=D0".replaceFirst( "=", "" );
        String[] arr = str.split( "=" );
        System.out.println( Arrays.toString( arr ) );

    }
}
