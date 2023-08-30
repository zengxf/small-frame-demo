package test.string.url;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CookieFormat {
    public static void main( String[] args ) throws Exception {

	String cookies = getContent();

	printCookie( cookies );

    }

    public static void printCookie( String cookies ) throws UnsupportedEncodingException {
	String[] cookie = cookies.split( ";\\s*" );
	for ( String coo : cookie ) {
	    String[] kv = coo.split( "=" );
	    System.out.println( String.format( "site.addCookie(\"%s\", \"%s\");", kv[0], URLDecoder.decode( kv[1], "UTF8" ) ) );
	}
    }

    public static String getContent() throws IOException {
	String path = CookieFormat.class.getResource( "" ).getPath() + "_cookie_format.txt";
	FileInputStream fis = new FileInputStream( path );
	byte[] arr = new byte[1024 * 10];
	int len = fis.read( arr );
	String url = new String( arr, 0, len );
	fis.close();
	return url;
    }
}
