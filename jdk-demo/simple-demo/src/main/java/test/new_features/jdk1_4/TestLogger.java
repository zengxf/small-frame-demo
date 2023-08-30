package test.new_features.jdk1_4;

import java.util.logging.Logger;

public class TestLogger {

    static Logger log = Logger.getGlobal();

    public static void main( String[] args ) {
	log.addHandler( new MyConsoleHandler() );
	log.info( "test" );
    }

}
