package test.jdkapi.lang;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestException {
    public static void main( String[] args ) {
        try {
            test1();
        } catch ( Exception e ) {
            System.out.println( e.getClass()
                    .getName() + ": " + e.getMessage() + "\n"
                    + Stream.of( e.getStackTrace() )
                            .limit( 2 )
                            .map( obj -> "\t at " + obj.toString() )
                            .collect( Collectors.joining( "\n" ) ) );
            System.out.println( "--------------" );
            e.printStackTrace();
        }
    }

    static void test1() throws Exception {
        test();
    }

    static void test() throws Exception {
        Exception e = new Exception( "test" );
        throw e;
    }
}
