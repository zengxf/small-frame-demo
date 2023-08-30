package test.jvm.reload_class.myCL;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static < T > T getProxy( T executer, Class<T> interfaceClass ) {

	InvocationHandler handler = ( proxy, method, args ) -> {
	    return method.invoke( executer, args );
	};

	Object proxy = Proxy.newProxyInstance( ProxyFactory.class.getClassLoader(), new Class[] { interfaceClass }, handler );

	@SuppressWarnings( "unchecked" )
	T proxy2 = (T) proxy;

	return proxy2;
    }

}
