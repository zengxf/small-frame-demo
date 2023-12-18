package cn.zxf.javaee7.test.javax.json;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObject;

public class TestToString {

    public static void main( String[] args ) {
        // Create Json and serialize
        JsonObject json = Json.createObjectBuilder() //
                .add( "name", "Falco" ) //
                .add( "age", BigDecimal.valueOf( 3 ) ) //
                .add( "biteable", Boolean.FALSE ).build();
        String result = json.toString();
        System.out.println( result );
    }

}
