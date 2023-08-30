package test.new_features.jdk1_7;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 测试方法句柄
 * 
 * @author zengxf
 */
public class TestMethodHandle {
    static class MyClass {
        public void test( String msg ) {
            System.out.println( "test msg => " + msg );
        }

        public static void testStatic( String msg ) {
            System.out.println( "test static msg => " + msg );
        }

        private static void testStaticPrivate( String msg ) {
            System.out.println( "test static private msg => " + msg );
        }

        private void testMemberPrivate( String msg ) {
            System.out.println( "test private msg => " + msg );
        }
    }

    public static void main( String[] args ) throws Throwable {
        testPrivate();
        testStatic();
        testMember();
    }

    /**
     * 测试私有方法
     * 
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws Throwable
     */
    static void testPrivate() {
        MyClass obj = new MyClass();

        MyClass.testStaticPrivate( "direct - test 01" );
        obj.testMemberPrivate( "direct - test 02" );

        try {
            MethodType mt = MethodType.methodType( void.class, String.class );
            // 也只能调用公共的方法
            MethodHandle mh = MethodHandles.lookup().findSpecial( Object.class, "testMemberPrivate", mt, MyClass.class );
            // MethodHandle mh = MethodHandles.lookup().findVirtual( MyClass.class, "testMemberPrivate", mt );

            mh.bindTo( obj ).invokeExact( "ok 1" );
            mh.invoke( obj, "ok 2" );
        } catch ( Throwable t ) {
            System.out.println( "私有方法不能调用！" );
        }
    }

    /**
     * 测试静态方法
     * 
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws Throwable
     */
    static void testStatic() throws NoSuchMethodException, IllegalAccessException, Throwable {
        MethodType mt = MethodType.methodType( void.class, String.class );
        MethodHandle mh = MethodHandles.lookup().findStatic( MyClass.class, "testStatic", mt );

        mh.invokeExact( "ok 1" );
        mh.invoke( "ok 2" );
    }

    /**
     * 测试成员方法
     * 
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws Throwable
     */
    static void testMember() throws NoSuchMethodException, IllegalAccessException, Throwable {
        MethodType mt = MethodType.methodType( void.class, String.class );
        MethodHandle mh = MethodHandles.lookup().findVirtual( MyClass.class, "test", mt );

        MyClass obj = new MyClass();
        mh.bindTo( obj ).invokeExact( "ok 1" );
        mh.invoke( obj, "ok 2" );
    }
}
