package cn.zxf.sonar_maven_project.test;

public class Test1Service {

    public void testZeroError() {
        int a = 0;
        int b = 1;
        int c = b / a;
        System.out.println( c );
    }

    public void testZeroError( int a ) {
        int b = 1;
        int c = b / a;
        System.out.println( c );
    }

    @SuppressWarnings( "null" )
    public void testNullError() {
        String a = null;
        System.out.println( a );
        System.out.println( a.substring( 1, 2 ) );
    }

    @SuppressWarnings( "null" )
    public void testNullError2() {
        String a = null;
        System.out.println( a );
        System.out.println( a.substring( 1, 2 ) );
    }

    public void testIndexError() {
        String[] a = { "a" };
        System.out.println( a );
        System.out.println( a[2] );
    }

}
