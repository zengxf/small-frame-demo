package cn.zxf.gson;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestMap {

    public static void main( String[] args ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put( "name", "mafly" );
        map.put( "age", "18" );
        map.put( "sex", null );

        String jsonString = new Gson().toJson( map );
        System.out.println( jsonString );

        String jsonString1 = new GsonBuilder().serializeNulls()
                .create()
                .toJson( map );
        System.out.println( jsonString1 );
    }

}
