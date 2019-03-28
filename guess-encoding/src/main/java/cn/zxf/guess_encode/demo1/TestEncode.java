package cn.zxf.guess_encode.demo1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

import com.glaforge.i18n.io.CharsetToolkit;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class TestEncode {

    public static void main( String[] args ) throws IOException {
        String gbkFile = TestEncode.class.getResource( "/test-gbk.txt" ).getFile();
        System.out.println( gbkFile );
        String utf8File = TestEncode.class.getResource( "/test-utf8.txt" ).getFile();
        System.out.println( utf8File );

        System.out.println( guessCharset( gbkFile ) );
        System.out.println( guessCharset( utf8File ) );

        test( gbkFile );
        test( utf8File );
    }

    public static Charset guessCharset( String file ) throws IOException {
        return guessCharset( new File( file ) );
    }

    public static Charset guessCharset( File file ) throws IOException {
        return CharsetToolkit.guessEncoding( file, 4096, StandardCharsets.UTF_8 );
    }

    public static void test( String file ) throws IOException {
        InputStream input = new FileInputStream( file );
        BufferedInputStream bis = new BufferedInputStream( input );
        CharsetDetector cd = new CharsetDetector();
        cd.setText( bis );
        CharsetMatch cm = cd.detect();

        if ( cm != null ) {
            String charset = cm.getName();
            System.out.println( "---> " + charset );
        } else {
            throw new UnsupportedCharsetException( "" );
        }
    }

}
