package cn.zxf.log4j2.test;

public class TestMain {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( TestMain.class );

    public static void main( String[] args ) {
        log.info( "test info" );
        log.warn( "test warn" );
        log.error( "test error" );
        System.out.println( "cn.zxf.log4j2.test.TestMain".length() );
    }

}
