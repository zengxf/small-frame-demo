package cn.zxf.guess_encode.demo3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChangeUtil {

    public static void change( Path from, Path to ) {
        change( from, "gbk", to, "utf-8" );
    }

    public static void change( Path from, String fromEncode, Path to ) {
        change( from, fromEncode, to, "utf-8" );
    }

    public static void change( Path from, String fromEncode, Path to, String toEncode ) {
        try {
            byte[] bf = Files.readAllBytes( from );
            String str1 = new String( bf, "gbk" );
            byte[] bf2 = str1.getBytes( "utf-8" );
            Files.write( to, bf2 );
        } catch ( IOException e ) {
            log.error( "from: {}, to: {}", from, to );
            log.error( "error: ", e );
        }
    }

}
