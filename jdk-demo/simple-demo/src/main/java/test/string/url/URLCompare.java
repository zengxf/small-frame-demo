package test.string.url;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class URLCompare {
    public static void main( String[] args ) throws Exception {

	String[] urls = getUrls();

	RequestVO v1 = analyzeOriginalURL( urls[0] );
	RequestVO v2 = analyzeOriginalURL( urls[1] );

	compare( v1, v2 );
    }

    public static void compare( RequestVO v1, RequestVO v2 ) {
	if ( !v1.getMainUrl().equals( v2.getMainUrl() ) ) {
	    System.out.println( "主URL不一样：" );
	    System.out.println( "v1: " + v1.getMainUrl() );
	    System.out.println( "v2: " + v2.getMainUrl() );
	}

	Map<String, String> temp = new HashMap<>();
	temp.putAll( v1.getParam() );
	temp.putAll( v2.getParam() );

	for ( String key : temp.keySet() ) {
	    String p1V = v1.getParam().get( key );
	    String p2V = v2.getParam().get( key );
	    if ( p1V == null ) {
		System.out.printf( "v1没有参数[%s]，v2: [%s] %n", key, p2V );
	    }
	    else if ( p2V == null ) {
		System.out.printf( "v2没有参数[%s]，v1: [%s] %n", key, p1V );
	    }
	    else if ( !p1V.equals( p2V ) ) {
		System.out.printf( "v1与v2参数[%s]不一致，v1：[%s]，v2: [%s] %n", key, p1V, p2V );
	    }
	}

	System.out.println( "比较完毕！" );
    }

    public static RequestVO analyzeOriginalURL( String url ) throws UnsupportedEncodingException {
	RequestVO vo = new RequestVO();

	String str = URLDecoder.decode( url, "UTF8" );
	str = str.replaceAll( "%2C|%2c", "," );
	str = str.replaceAll( "%25", "%" );
	str = str.replaceAll( "%2B|%2b", "+" );
	String[] params = str.split( "&|\\?" );

	vo.setMainUrl( params[0] );
	for ( int i = 1; i < params.length; i++ ) {
	    String param = params[i];
	    String[] kv = param.split( "=" );
	    vo.getParam().put( kv[0], kv.length > 1 ? kv[1] : "无" );
	}

	return vo;
    }

    public static String[] getUrls() throws IOException {
	String[] urls = new String[2];
	String path = URLCompare.class.getResource( "" ).getPath() + "_url_compara.txt";

	FileInputStream fis = new FileInputStream( path );
	InputStreamReader isr = new InputStreamReader( fis, "UTF8" );
	BufferedReader br = new BufferedReader( isr );

	urls[0] = br.readLine();
	urls[1] = br.readLine();

	br.close();

	return urls;
    }
}
