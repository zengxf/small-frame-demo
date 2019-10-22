package cn.zxf;

import java.util.HashMap;
import java.util.Map;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class TestDozer {

    public static void main( String[] args ) {
        User user = User.testData();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        UserDto dto = mapper.map( user, UserDto.class );
        System.out.println( user );
        System.out.println( "================== Dto ==================" );
        System.out.println( dto );

        Map<String, Object> map = new HashMap<>();
        mapper.map( user, map );
        System.out.println( "================== Map ==================" );
        System.out.println( map );

        dto = mapper.map( map, UserDto.class );
        System.out.println( "================== Dto2 ==================" );
        System.out.println( dto );
    }

}
