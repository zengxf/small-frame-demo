package cn.zxf.gson;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestObject {

    public static void main( String[] args ) throws IOException {
        UserInfo info = UserInfo.builder()
                .name( "zxf" )
                .age( 12 )
                // .sex( "man" )
                .build();

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.setSerializationInclusion( Include.NON_NULL )
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString( info );
        System.out.println( json );

        UserInfo info2 = mapper.readValue( json, UserInfo.class );
        System.out.println( info2 );
    }

}
