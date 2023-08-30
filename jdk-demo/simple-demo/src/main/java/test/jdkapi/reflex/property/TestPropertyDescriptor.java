package test.jdkapi.reflex.property;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import lombok.Data;

/**
 * 利用内省找 java bean 属性 和 get, set 方法
 * 
 * <p>
 * Created by zengxf on 2017-11-24
 */
public class TestPropertyDescriptor {

    public static void main( String[] args ) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo( User.class );
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for ( PropertyDescriptor pd : pds ) {
            System.out.println( pd.getName() );
            System.out.println( pd.getWriteMethod() );
            System.out.println( pd.getReadMethod() );
            System.out.println();
        }
    }

    @Data
    public static class User {
        private String name;
        private int    age;
    }

}
