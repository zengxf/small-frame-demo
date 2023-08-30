package test.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetUrlSign {

    public static void main( String[] args ) {
	companyId();
    }

    static void companyId() {
	String url = "http://jobs.51job.com/all/co2931339.html";
	String regex = "/(co)?(\\d+)\\.html";
	Pattern p = Pattern.compile( regex );
	Matcher m = p.matcher( url );
	if ( m.find() )
	    System.out.println( m.group( 2 ) );
    }

    static void positionId() {
	String url = "http://jobs.51job.com/beijing/83202634.html?s=01&t=0";
	String regex = "/(\\d+)\\.html";
	Pattern p = Pattern.compile( regex );
	Matcher m = p.matcher( url );
	if ( m.find() )
	    System.out.println( m.group( 1 ) );
    }

    static void getSign() {
	String url = "http://jobs.51job.com/all/co3979364.html";
	String id = url.substring( url.lastIndexOf( "/" ) + 1, url.lastIndexOf( ".html" ) );
	System.out.println( id );
    }

}
