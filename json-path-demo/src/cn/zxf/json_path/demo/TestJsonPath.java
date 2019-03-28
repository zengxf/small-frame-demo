package cn.zxf.json_path.demo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.JsonContext;

public class TestJsonPath {
    public static void main( String[] args ) throws URISyntaxException, IOException {
	URI uri = TestJsonPath.class.getResource( "test.json" ).toURI();
	Path path = Paths.get( uri );
	String json = Files.readAllLines( path, Charset.forName( "UTF-8" ) ).stream().collect( Collectors.joining( "\n" ) );
	System.out.println( json );
	System.out.println( "------------------------------" );

	JsonContext jsonContext = new JsonContext();
	JsonContext parse = (JsonContext) jsonContext.parse( json );
	JsonPath jsonPath = JsonPath.compile( "$.store.book[*].author" );
	List<String> authors = parse.read( jsonPath );
	System.out.println( authors );
    }
}
