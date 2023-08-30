package test.jdkapi.reflex.introspector;

import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class TestIntrospector {

    public static void main( String[] args ) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo( UserInfo.class );

        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        for ( PropertyDescriptor propDesc : proDescrtptors ) {
            System.out.println( propDesc );

            Method setMethod = propDesc.getWriteMethod();
            System.out.println( setMethod );

            Method getMethod = propDesc.getReadMethod();
            System.out.println( getMethod );

            System.out.println( "================" );
        }

        System.out.println( "\n---------------------\n" );
        EventSetDescriptor[] eventArr = beanInfo.getEventSetDescriptors();
        for ( EventSetDescriptor event : eventArr ) {
            System.out.println( event.getDisplayName() + "\t" + event.getName() );
        }

        System.out.println( "---------------------\n" );
        MethodDescriptor[] methodArr = beanInfo.getMethodDescriptors();
        for ( MethodDescriptor method : methodArr ) {
            System.out.println( method.getDisplayName() );
        }
    }
}
