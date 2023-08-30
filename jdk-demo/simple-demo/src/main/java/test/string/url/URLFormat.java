package test.string.url;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLFormat {
    public static void main( String[] args ) throws Exception {

	String url = getUrl();

	printOriginalURL( url );

    }

    public static void printOriginalURL( String url ) throws UnsupportedEncodingException {
	String str = URLDecoder.decode( url, "UTF8" );
	str = str.replaceAll( "%2C|%2c", "," );
	str = str.replaceAll( "%25", "%" );
	str = str.replaceAll( "%2B|%2b", "+" );
	String[] params = str.split( "&|\\?" );
	for ( String param : params ) {
	    System.out.println( param );
	}
	// System.out.println( str );
    }

    public static String getUrl() throws IOException {
	String path = URLFormat.class.getResource( "" ).getPath() + "_url_format.txt";
	FileInputStream fis = new FileInputStream( path );
	byte[] arr = new byte[1024 * 10];
	int len = fis.read( arr );
	String url = new String( arr, 0, len );
	fis.close();
	return url;
    }
}
