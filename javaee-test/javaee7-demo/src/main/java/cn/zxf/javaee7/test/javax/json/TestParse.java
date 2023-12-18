package cn.zxf.javaee7.test.javax.json;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class TestParse {
    public static void main( String[] args ) {
        // Parse back
        final String result = "{\"name\":\"Falco\",\"age\":3,\"bitable\":false}";
        JsonParser parser = Json.createParser( new StringReader( result ) );
        String key = null;
        String value = null;
        while ( parser.hasNext() ) {
            final Event event = parser.next();
            switch ( event ) {
                case KEY_NAME:
                    key = parser.getString();
                    System.out.println( key );
                    break;
                case VALUE_STRING:
                    value = parser.getString();
                    System.out.println( value );
                    break;
                default:
                    System.out.println( "===> " + event );
                    break;
            }
        }
        parser.close();

        JsonReader reader = Json.createReader( new StringReader( result ) );
        JsonObject jobj = reader.readObject();
        System.out.println( "json-object: " + jobj );
    }
}
