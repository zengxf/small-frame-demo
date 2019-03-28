package cn.zxf.guess_encode.demo3;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestChange {

    static Map<String, String> pathOfEncode = new HashMap<>();
    static {
        pathOfEncode.put( "test11/test1-gbk.txt", "utf-8" );
        pathOfEncode.put( "test11/test112/test2-gbk.txt", "utf-8" );

        pathOfEncode.put( "src/cn/simple/test/algorithm/bloom/BloomFilter2.java", "UTF-8" );
        pathOfEncode.put( "src/cn/simple/test/algorithm/bloom/bloomFilter.java", "UTF-8" );
        pathOfEncode.put( "src/cn/simple/test/jdkapi/awt/HtmlToImage.java", "UTF-8" );
        pathOfEncode.put( "src/cn/simple/test/new_features/jdk1_4/nio/TestBuffer.java", "UTF-8" );
        pathOfEncode.put( "src/cn/simple/test/new_features/jdk1_8/date/LocalDatePage.java", "UTF-8" );
        pathOfEncode.put( "src/cn/simple/test/temp/TempTest.java", "UTF-8" );

    }

    public static void main( String[] args ) throws URISyntaxException {
        testInner();
        // Path file = Paths.get( "M:\\project\\zxf_super_demo\\simple-demo\\src" );
        // System.out.println( file );
        // change( file );
    }

    static void testInner() throws URISyntaxException {
        URI uri = TestChange.class.getResource( "/" ).toURI();
        Path path = Paths.get( uri );
        // Path file = path.resolve( "../src/main/resources/test1" ).normalize();
        Path file = path.resolve( "../src/main/resources/test11" ).normalize();
        System.out.println( file );
        change( file );
    }

    static void change( Path file ) {
        if ( !Files.isDirectory( file ) )
            return;
        try {
            Files.list( file ).forEach( item -> {
                if ( Files.isDirectory( item ) ) {
                    change( item );
                } else if ( Files.isRegularFile( item ) ) {
                    // Path name = item.getFileName();
                    // Path to = item.getParent().resolve( "utf8-" + name );
                    String oldEncode = encode( item );
                    System.out.println( item + " ===> " + oldEncode );
                    // ChangeUtil.change( item, oldEncode, item );
                } else {
                    log.warn( "不是正规文件：{}", item );
                }
            } );
        } catch ( IOException e ) {
            log.error( "file: {}", file );
            log.error( "error: ", e );
        }
    }

    static String encode( Path path ) {
        return pathOfEncode.keySet() //
                .stream() //
                .filter( key -> path.endsWith( key ) ) //
                .findFirst() //
                .map( key -> pathOfEncode.get( key ) ) //
                .orElse( "gbk" );
    }

}
