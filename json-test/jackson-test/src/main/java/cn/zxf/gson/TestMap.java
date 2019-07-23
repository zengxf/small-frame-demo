package cn.zxf.gson;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMap {

    public static void main( String[] args ) throws IOException {
        Map<String, Object> map = Map.of( "name", "zxf", "age", 22, "sex", "man" );

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.setSerializationInclusion( Include.NON_NULL )
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString( map );
        System.out.println( json );

        Map<String, Object> map2 = mapper.readValue( json, new TypeReference<Map<String, Object>>() {
        } );
        System.out.println( map2 );
        System.out.println( map2.getClass() );
    }

}
