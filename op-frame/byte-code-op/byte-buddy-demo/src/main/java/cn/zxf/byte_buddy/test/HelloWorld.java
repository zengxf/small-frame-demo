package cn.zxf.byte_buddy.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloWorld {

    public static void main( String[] args ) throws Exception {
        Class<?> dynamicType = new ByteBuddy() //
                .subclass( Object.class )
                .method( ElementMatchers.named( "toString" ) )
                .intercept( FixedValue.value( "Hello World!" ) )
                .make()
                .load( HelloWorld.class.getClassLoader() )
                .getLoaded();
        String toStr = dynamicType.getDeclaredConstructor()
                .newInstance()
                .toString();
        System.out.println( toStr );
    }

}
