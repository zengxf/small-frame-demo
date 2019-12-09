package cn.zxf;

import org.springframework.beans.BeanUtils;

public class TestSpringBeanUtils {

    public static void main( String[] args ) {
        User user = User.testData();
        UserDto dto = new UserDto();
        BeanUtils.copyProperties( user, dto );
        System.out.println( user );
        System.out.println( "==================" );
        System.out.println( dto );
    }

}
