package cn.zxf.test.log4j;

import org.apache.log4j.Logger;

public class TestLog {
    static Logger log = Logger.getLogger( TestLog.class );

    public static void main( String[] args ) {
        log.debug( "zxf logging start" );

        log.info( "zxf login success" );

        log.warn( "zxf login password error" );
        log.error( "zxf attack web site" );

        log.debug( "zxf logging end" );
    }

}
