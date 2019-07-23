package cn.zxf.fastjson;

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class TestMap {

    public static void main( String[] args ) {
        Map<String, Object> map = Map.of( "name", "zxf", "age", 22, "sex", "man" );

        String json = JSON.toJSONString( map );
        System.out.println( json );

        Map<String, Object> map2 = JSON.parseObject( json );
        System.out.println( map2 );
        System.out.println( map2.getClass() );
    }

}
