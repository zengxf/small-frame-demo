package cn.zxf.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestExpose {

    public static void main( String[] args ) {
        UserInfo user = UserInfo.builder()
                .userName( "zxf" )
                .age( 22 )
                .sex( "男" )
                .build();

        String jsonString = new Gson().toJson( user );
        System.out.println( jsonString );

        String jsonString1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson( user );
        System.out.println( jsonString1 );
    }

}
