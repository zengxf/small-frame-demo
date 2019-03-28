package cn.zxf.agent;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestReadFile {

    public static void main( String[] args ) throws URISyntaxException, IOException {
        URI uri = GreetingTransformer.class.getResource( "/classes/Dog.class.file" ).toURI();
        Path path = Paths.get( uri );
        byte[] bytearr = Files.readAllBytes( path );
        System.out.println( path );
        System.out.println( bytearr.length );
    }

}
