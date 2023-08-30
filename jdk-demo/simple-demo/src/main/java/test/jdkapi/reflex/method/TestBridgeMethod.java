package test.jdkapi.reflex.method;

import java.lang.reflect.Method;

// M:\project\zxf_super_demo\simple-demo\bin\main\test\jdkapi\reflex\method
public class TestBridgeMethod {

    public static interface SuperClass< T > {
        T method( T param );
    }

    public static class SubClass implements SuperClass<String> {
        public String method( String param ) {
            return param;
        }
    }

    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public static void main( String[] args ) {
        try {
            Method m = SubClass.class.getMethod( "method", String.class );
            System.out.println( m.isBridge() );
            m = SubClass.class.getMethod( "method", Object.class );
            System.out.println( m.isBridge() );
            System.out.println( "---------------" );

            SuperClass superClass = new SubClass();
            System.out.println( superClass.method( "abc123" ) ); // 调用的是实际的方法
            System.out.println( superClass.method( new Object() ) ); // 调用的是桥接方法
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
