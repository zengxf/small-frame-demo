package cn.zxf.gson;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestGeneric {

    public static void main( String[] args ) throws IOException {
        UserInfo info = UserInfo.builder()
                .name( "zxf" )
                .age( 12 )
                .sex( "man" )
                .build();

        List<UserInfo> list = List.of( info );

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.setSerializationInclusion( Include.NON_NULL )
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString( list );
        System.out.println( json );

        List<UserInfo> list2 = mapper.readValue( json, new TypeReference<List<UserInfo>>() {
        } );
        System.out.println( list2 );
    }

}
