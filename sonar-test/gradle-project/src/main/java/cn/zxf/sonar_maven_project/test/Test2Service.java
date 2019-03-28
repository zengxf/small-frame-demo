package cn.zxf.sonar_maven_project.test;

public class Test2Service {

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

    @SuppressWarnings( "null" )
    public void testNullError3() {
        String a = null;
        System.out.println( a );
        System.out.println( a.substring( 1, 2 ) );
    }

    public void testIndexError() {
        String[] a = { "a" };
        System.out.println( a );
        System.out.println( a[2] );
    }

    public void testRecurseError() {
        String[] a = { "a" };
        System.out.println( a );
        testRecurseError();
    }

}
