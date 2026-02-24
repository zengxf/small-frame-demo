package cn.zxf.gson;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestGeneric {

    public static void main( String[] args ) {
        UserInfo info = UserInfo.builder()
                .userName( "zxf" )
                .age( 12 )
                .sex( "man" )
                .build();

        List<UserInfo> list = List.of( info );

        Gson gson = new Gson();

        String json = gson.toJson( list );
        System.out.println( json );

        Type type = new TypeToken<List<UserInfo>>() {
        }.getType();

        List<UserInfo> list2 = gson.fromJson( json, type );
        System.out.println( list2 );
    }

}
