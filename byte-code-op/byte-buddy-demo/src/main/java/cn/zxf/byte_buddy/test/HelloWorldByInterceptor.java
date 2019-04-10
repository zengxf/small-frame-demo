package cn.zxf.byte_buddy.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloWorldByInterceptor {

    public static void main( String[] args ) throws Exception {
        Class<?> dynamicType = new ByteBuddy() //
                .subclass( Object.class )
                .method( ElementMatchers.named( "toString" ) )
                .intercept( MethodDelegation.to( ToStringInterceptor.class ) )
                .make()
                .load( HelloWorldByInterceptor.class.getClassLoader() )
                .getLoaded();
        String toStr = dynamicType.getDeclaredConstructor()
                .newInstance()
                .toString();
        System.out.println( toStr );
    }

    public static class ToStringInterceptor {
        public static String intercept() {
            return "Hello World!";
        }
    }

}
