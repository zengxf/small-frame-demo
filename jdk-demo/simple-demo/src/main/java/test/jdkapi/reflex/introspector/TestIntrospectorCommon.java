package test.jdkapi.reflex.introspector;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试内省：验证顺序：首先 isXX()，没有时，用 getXX()；再是 setXX()
 * 
 * <p>
 * Created by zengxf on 2018-04-11
 */
public class TestIntrospectorCommon {

    public static void main( String[] args ) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // testCommonSet();
        System.out.println( "=================" );
        // testCommonGet();
        System.out.println( "=================" );
        testErrorSet();
        System.out.println( "=================" );
        // testErrorGet();
    }

    static void testCommonSet() throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        UserInfo userInfo = new UserInfo();

        PropertyDescriptor propDesc = new PropertyDescriptor( "name", UserInfo.class );
        Method setMethod = propDesc.getWriteMethod();
        setMethod.invoke( userInfo, "wong" );
        System.out.println( "setMethod: " + setMethod );
        System.out.println( "userInfo: " + userInfo );
    }

    static void testCommonGet() throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        UserInfo userInfo = new UserInfo();
        userInfo.setName( "test" );

        PropertyDescriptor propDesc = new PropertyDescriptor( "name", UserInfo.class );
        Method getMethod = propDesc.getReadMethod();
        Object name = getMethod.invoke( userInfo );
        System.out.println( "getMethod: " + getMethod );
        System.out.println( "userInfo: " + userInfo );
        System.out.println( "name: " + name );
    }

    static void testErrorSet() throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        UserErrorInfo userInfo = new UserErrorInfo();

        PropertyDescriptor propDesc = new PropertyDescriptor( "name", UserErrorInfo.class );
        Method setMethod = propDesc.getWriteMethod();
        Object name = setMethod.invoke( userInfo, "test" );
        System.out.println( "setMethod: " + setMethod );
        System.out.println( "userInfo: " + userInfo );
        System.out.println( "name: " + name );
    }

    static void testErrorGet() throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        UserErrorInfo userInfo = new UserErrorInfo();

        PropertyDescriptor propDesc = new PropertyDescriptor( "name", UserErrorInfo.class );
        Method getMethod = propDesc.getReadMethod();
        Object name = getMethod.invoke( userInfo );
        System.out.println( "getMethod: " + getMethod );
        System.out.println( "userInfo: " + userInfo );
        System.out.println( "name: " + name );
    }

}
