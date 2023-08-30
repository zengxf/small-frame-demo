package test.new_features.jdk1_6.apt;

import java.util.HashMap;

@MySlf4j( system = "Vlogging", module = "Integration" )
public class VloggingAnnotated {

    public static void main( String[] args ) {
	HashMap<String, String> params = new HashMap<>();
	params.put( "foo", "xyz" );
	System.out.println( "test" );
    }

}