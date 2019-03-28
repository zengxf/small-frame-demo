package cn.zxf.guess_encode.demo2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestChange {

    public static void main( String[] args ) throws URISyntaxException, IOException {
        URI uri = TestChange.class.getResource( "/" ).toURI();
        Path path = Paths.get( uri );
        Path file = path.resolve( "../src/main/resources/test-change-gbk.txt" ).normalize();
        Path toFile = path.resolve( "../src/main/resources/test-change-utf8.txt" ).normalize();
        System.out.println( file );
        System.out.println( toFile );
        byte[] bf = Files.readAllBytes( file );
        String str1 = new String( bf, "gbk" );
        byte[] bf2 = str1.getBytes( "utf-8" );
        Files.write( toFile, bf2 );
    }

}
