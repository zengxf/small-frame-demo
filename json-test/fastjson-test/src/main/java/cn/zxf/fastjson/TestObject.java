package cn.zxf.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class TestObject {

    public static void main( String[] args ) {
        UserInfo info = UserInfo.builder()
                .name( "zxf" )
                .age( 12 )
                // .sex( "man" )
                .build();

        String json = JSON.toJSONString( info, SerializerFeature.WriteMapNullValue );
        System.out.println( json );

        UserInfo info2 = JSON.parseObject( json, UserInfo.class );
        System.out.println( info2 );
    }

}
