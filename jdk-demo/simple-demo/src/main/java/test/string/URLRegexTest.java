package test.string;

import java.util.regex.Pattern;

public class URLRegexTest {

    public static void main( String[] args ) {

	String protoUrlRegex = "http://search.51job.com/";
	String urlRegex = getUrlRegex( protoUrlRegex );

	System.out.println( protoUrlRegex );
	System.out.println( urlRegex );

	Pattern p = Pattern.compile( urlRegex );
	String url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=010000,020000,030200,04000";

	boolean res = p.matcher( url ).matches();
	System.out.println( res );

    }

    /**
     * 返回匹配URL的正则
     * 
     * @param protoUrlRegex
     * @return
     */
    public static String getUrlRegex( String protoUrlRegex ) {
	String reg = protoUrlRegex;
	reg = reg.replace( ".", "\\." );
	reg = reg.replace( "?", "\\?" );
	reg += ".*";
	return reg;
    }

}
