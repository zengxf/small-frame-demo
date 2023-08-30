package test.new_features.jdk1_8.base_type;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStringInt {
    public static void main( String[] args ) {

        // chars();

        // splitAsStream();

        // asPredicate();

        // unsigned();

        // addExact();
    }

    static void addExact() {
        try {
            Math.addExact( Integer.MAX_VALUE, 1 );
        } catch ( ArithmeticException e ) {
            System.err.println( e.getMessage() );
        }
    }

    static void unsigned() {
        long maxUnsignedInt = ( 1l << 32 ) - 1;
        String string = String.valueOf( maxUnsignedInt );
        int unsignedInt = Integer.parseUnsignedInt( string, 10 );
        System.out.println( unsignedInt );
        String string2 = Integer.toUnsignedString( Integer.MIN_VALUE, 10 );
        System.out.println( Integer.MAX_VALUE );
        System.out.println( string2 );
    }

    static void asPredicate() {
        Pattern pattern = Pattern.compile( ".*@gmail\\.com" );
        long c = Stream.of( "bob@gmail.com", "alice@hotmail.com" )//
                .filter( pattern.asPredicate() )//
                .count();
        System.out.println( c );
    }

    static void splitAsStream() {
        String v = Pattern.compile( ":" )//
                .splitAsStream( "foobar:foo:bar" )//
                .filter( s -> s.contains( "bar" ) )//
                .sorted()//
                .collect( Collectors.joining( ":" ) );
        System.out.println( v );
    }

    static void chars() {
        String s = "foobar:foo:bar"//
                .chars()//
                .distinct()//
                .mapToObj( c -> String.valueOf( (char) c ) )//
                .sorted()//
                .collect( Collectors.joining() );
        System.out.println( s );
    }
}
