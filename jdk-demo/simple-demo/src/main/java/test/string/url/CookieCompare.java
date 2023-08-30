package test.string.url;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class CookieCompare {
    public static void main( String[] args ) throws Exception {

	String[] urls = getCookies();

	RequestVO v1 = analyzeOriginalCookie( urls[0] );
	RequestVO v2 = analyzeOriginalCookie( urls[1] );

	compare( v1, v2 );
    }

    public static void compare( RequestVO v1, RequestVO v2 ) {

	Map<String, String> temp = new HashMap<>();
	temp.putAll( v1.getParam() );
	temp.putAll( v2.getParam() );

	for ( String key : temp.keySet() ) {
	    String p1V = v1.getParam().get( key );
	    String p2V = v2.getParam().get( key );
	    if ( p1V == null ) {
		System.out.printf( "v1没有Cookie[%s]，v2: [%s] %n", key, p2V );
	    }
	    else if ( p2V == null ) {
		System.out.printf( "v2没有Cookie[%s]，v1: [%s] %n", key, p1V );
	    }
	    else if ( !p1V.equals( p2V ) ) {
		System.out.printf( "v1与v2的Cookie[%s]不一致，v1：[%s]，v2: [%s] %n", key, p1V, p2V );
	    }
	}

	System.out.println( "比较完毕！" );
    }

    public static RequestVO analyzeOriginalCookie( String cookie ) throws UnsupportedEncodingException {
	RequestVO vo = new RequestVO();

	String str = URLDecoder.decode( cookie, "UTF8" );
	// str = str.replaceAll( "%2C|%2c", "," );
	String[] params = str.split( ";\\s*" );

	for ( int i = 1; i < params.length; i++ ) {
	    String param = params[i];
	    String[] kv = param.split( "=" );
	    vo.getParam().put( kv[0], kv.length > 1 ? kv[1] : "无" );
	}

	return vo;
    }

    public static String[] getCookies() throws IOException {
	String[] urls = new String[2];
	String path = CookieCompare.class.getResource( "" ).getPath() + "_compara_cookie.txt";

	FileInputStream fis = new FileInputStream( path );
	InputStreamReader isr = new InputStreamReader( fis, "UTF8" );
	BufferedReader br = new BufferedReader( isr );

	urls[0] = br.readLine();
	urls[1] = br.readLine();

	br.close();

	return urls;
    }
}
