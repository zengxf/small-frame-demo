package cn.zxf.fastjson;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class TestGeneric {

    public static void main( String[] args ) {
        UserInfo info = UserInfo.builder()
                .name( "zxf" )
                .age( 12 )
                .sex( "man" )
                .build();

        List<UserInfo> list = List.of( info );

        String json = JSON.toJSONString( list );
        System.out.println( json );

        List<UserInfo> list2 = JSON.parseObject( json, new TypeReference<List<UserInfo>>() {
        } );
        System.out.println( list2 );
    }

}
