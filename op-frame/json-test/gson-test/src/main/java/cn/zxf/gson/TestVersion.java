package cn.zxf.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestVersion {

    public static void main( String[] args ) {
        UserInfo user = UserInfo.builder()
                .userName( "zxf" )
                .age( 22 )
                .sex( "男" )
                .build();

        String jsonString = new Gson().toJson( user );
        System.out.println( jsonString );

        String jsonString1 = new GsonBuilder().setVersion( 2.4D ) // 2.4, 2.6, 2.7, 2.8
                .create()
                .toJson( user );
        System.out.println( jsonString1 );
    }

}
