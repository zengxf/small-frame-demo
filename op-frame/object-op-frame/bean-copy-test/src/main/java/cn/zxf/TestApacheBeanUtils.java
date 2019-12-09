package cn.zxf;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class TestApacheBeanUtils {

    public static void main( String[] args ) throws IllegalAccessException, InvocationTargetException {
        User user = User.testData();
        UserDto dto = new UserDto();
        BeanUtils.copyProperties( dto, user );
        System.out.println( user );
        System.out.println( "==================" );
        System.out.println( dto );
    }

}
