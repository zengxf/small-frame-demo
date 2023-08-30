package test.jvm.reload_class.myCL;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ZMain {
    public static void main( String[] args ) throws Exception {
        testMyUrlCL();
        // testMyCL();
    }

    static void testMyUrlCL() throws Exception {
        MyUrlClassLoader loader = new MyUrlClassLoader();
        Class<?> clazz = loader.loadClass( "cn.simple.test.reload_class.myCL.TestFunction" );
        System.out.println( clazz );

        IFunction fun = (IFunction) clazz.getDeclaredConstructor()
                .newInstance();
        fun.test();
    }

    static void testCLProxy() throws Exception {
        MyClassLoader loader = new MyClassLoader();
        Class<?> clazz = loader.loadClass( "cn.simple.test.reload_class.myCL.TestFunction" );
        System.out.println( clazz );

        IFunction fun = (IFunction) clazz.getDeclaredConstructor()
                .newInstance();
        fun.test();

        IFunction fun2 = ProxyFactory.getProxy( fun, IFunction.class );
        fun2.test();
    }

    static void testMyCL() throws Exception {
        MyClassLoader loader = new MyClassLoader();
        Class<?> clazz = loader.loadClass( "cn.simple.test.reload_class.myCL.TestFunction" );
        System.out.println( clazz );

        IFunction fun = (IFunction) clazz.getDeclaredConstructor()
                .newInstance();
        fun.test();
    }

    static void testProxy() {
        IFunction fun = new TestFunction();

        IFunction funProxy = (IFunction) Proxy.newProxyInstance( ZMain.class.getClassLoader(), new Class[] { IFunction.class }, new InvocationHandler() {

            @Override
            public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
                return method.invoke( fun, args );
            }

        } );

        funProxy.test();

        fun.test();

        IFunction fun2 = ProxyFactory.getProxy( fun, IFunction.class );
        fun2.test();
    }
}
