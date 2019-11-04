package cn.zxf.test.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLog {
    static Logger log = LogManager.getLogger( TestLog.class );

    public static void main( String[] args ) {
        for ( int i = 0; i < 2; i++ ) {
            test();
        }
    }

    static void test() {
        long start = System.currentTimeMillis();

        log.debug( "zxf logging start" );

        log.info( "zxf login success" );

        log.warn( "zxf login password error" );
        log.error( "zxf attack web site" );

        long end = System.currentTimeMillis();
        log.debug( "zxf logging end - use times: {} ms", end - start );
    }

}
