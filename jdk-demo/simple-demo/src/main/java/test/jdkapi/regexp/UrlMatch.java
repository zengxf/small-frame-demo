package test.jdkapi.regexp;

import java.util.regex.Pattern;

public class UrlMatch {
    public static void main( String[] args ) {
	String LIST_URL_PATTERN = "https://www\\.liepin\\.com/zhaopin[^\\s]+";
	String url = "https://www.liepin.com/zhaopin?pubTime&salary&clean_condition&jobKind=2&isAnalysis&init=-1&sortFlag=15&industries&dqs&curPage=0&key=%E5%8C%96%E5%A6%86%E5%93%81%E5%85%AC%E5%8F%B8";

	boolean res = Pattern.compile( LIST_URL_PATTERN ).matcher( url ).matches();
	System.out.println( res );

    }
}
