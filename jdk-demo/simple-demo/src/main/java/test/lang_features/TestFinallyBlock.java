package test.lang_features;

import java.util.HashMap;
import java.util.Map;

// 参考：https://www.cnblogs.com/lanxuezaipiao/p/3440471.html
// 用 XJad 反编译查看更直观
// M:\zxf-demo-github\zxf_super_demo\simple-demo\bin\main\test\lang_features
public class TestFinallyBlock {
    public static void main( String[] args ) {
        // System.out.println( test1() );
        // System.out.println( test11() );
        // System.out.println( test2() );
        // System.out.println( test3() );
        // System.out.println( getMap().get( "KEY" )
        // .toString() );
        System.out.println( test5() );
    }

    public static int test5() {
        int b = 20;
        try {
            System.out.println( "try block" );
            b = b / 0;
            return b += 80;
        } catch ( Exception e ) {
            System.out.println( "catch block" );
            return b += 15;
        } finally {
            System.out.println( "finally block" );
            if ( b > 25 ) {
                System.out.println( "b>25, b = " + b );
            }
            b += 50;
        }
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put( "KEY", "INIT" );

        try {
            map.put( "KEY", "TRY" );
            return map;
        } catch ( Exception e ) {
            map.put( "KEY", "CATCH" );
        } finally {
            map.put( "KEY", "FINALLY" );
            map = null;
        }

        return map;
    }

    public static int test3() {
        int b = 20;
        try {
            System.out.println( "try block" );
            return b += 80;
        } catch ( Exception e ) {
            System.out.println( "catch block" );
        } finally {
            System.out.println( "finally block" );
            if ( b > 25 ) {
                System.out.println( "b>25, b = " + b );
            }
            b = 150;
        }
        return 2000;
    }

    @SuppressWarnings( "finally" )
    public static int test2() {
        int b = 20;
        try {
            System.out.println( "try block" );
            return b += 80;
        } catch ( Exception e ) {
        } finally {
            System.out.println( "finally block" );
            if ( b > 25 ) {
                System.out.println( "b>25, b = " + b );
            }
            return b += 2;
        }
    }

    public static String test11() {
        try {
            System.out.println( "try block" );
            return test12();
        } finally {
            System.out.println( "finally block" );
        }
    }

    public static String test12() {
        System.out.println( "return statement" );
        return "after return";
    }

    public static int test1() {
        int b = 20;
        try {
            System.out.println( "try block" );
            return b += 80;
        } catch ( Exception e ) {
        } finally {
            System.out.println( "finally block" );
            if ( b > 25 ) {
                System.out.println( "b>25, b = " + b );
            }
        }
        return b;
    }
}
