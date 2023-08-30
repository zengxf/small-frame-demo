package test.jdkapi.regexp;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class BuildRegexByURL {
    public static void main( String[] args ) throws UnsupportedEncodingException {
	String url = "https://www.baidu.com/s?ie=utf-8&f=3&rsv_bp=6";
	getRegex( url );
	
	test();
    }

    static void getRegex( String url ) {
	StringBuilder sb = new StringBuilder();

	int paramIndex = url.indexOf( "?" );
	String website = paramIndex > -1 ? url.substring( 0, paramIndex ) : url;
	sb.append( website.replace( ".", "\\." ) );

	if ( paramIndex > -1 ) {

	    sb.append( "\\?" );

	    String paramStr = url.substring( paramIndex + 1 );

	    String[] paramArr = paramStr.split( "&" );
	    for ( String param : paramArr ) {
		String[] kv = param.split( "=" );
		sb.append( kv[0] );
		if ( kv.length > 1 ) {
		    sb.append( "=.*" );
		}
		sb.append( "&" );
	    }

	    sb.deleteCharAt( sb.length() - 1 );
	}
	System.out.println( sb );
	String LIST_URL_PATTERN = "https://www\\.baidu\\.com/s\\?ie=.*&f=.*&rsv_bp=.*";
	System.out.println( LIST_URL_PATTERN );
    }

    static void test() {
	String LIST_URL_PATTERN = "https://www\\.baidu\\.com/s\\?ie=.*&f=.*&rsv_bp=.*";
	String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=6";

	boolean res = Pattern.compile( LIST_URL_PATTERN ).matcher( url ).matches();
	System.out.println( res );
    }
}
