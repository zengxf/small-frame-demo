package test.jdkapi.reflex.compiler.string;

/**
 * [参考](http://www.cnblogs.com/eoss/p/6136943.html)
 * 
 * <p>
 * Created by zengxf on 2018-04-11
 */
public class TestMain {

    public static void main( String[] args ) throws Exception {
        testPackage();
        System.out.println( "-----------------" );
        nonPackage();
    }

    static void testPackage() throws Exception {
        String java = "package cn.zxf.test; public class Test{ public void test() { System.out.println(1234); } }";
        System.out.println( java );

        DynamicEngine eng = DynamicEngine.getInstance();
        Object obj = eng.javaCodeToObject( "cn.zxf.test.Test", java );
        System.out.println( obj );
    }

    static void nonPackage() throws Exception {
        String java = "public class Test{ public void test() { System.out.println(1234); } }";
        System.out.println( java );

        DynamicEngine eng = DynamicEngine.getInstance();
        Object obj = eng.javaCodeToObject( "Test", java );
        System.out.println( obj );
    }

}
