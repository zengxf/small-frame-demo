package test.jdkapi.io.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageToBase64 {
    public static void main( String[] args ) throws URISyntaxException, IOException {
        Path src = Paths.get( "test.png" );
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        Files.copy( src, byteOut );
        byte[] content = byteOut.toByteArray();
        String b64 = Base64.getEncoder()
                .encodeToString( content );
        System.out.println( b64 );
    }
}
