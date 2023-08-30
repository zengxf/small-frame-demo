package test.jdkapi.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateFontSize {
    public static void main( String[] args ) {
	String html = "<p>aaaaa</p><p><span style=\"font-size: 12px;\">b<strong>bb</strong>b</span></p><p><span style=\"font-size: 18px; background-color: rgb(238, 236, 225);\">ccc<strong>ccc</strong></span></p>";
	System.out.println( html );

	String newHtml = html;
	String regex = "font-size: (\\d+)px;";
	Pattern p = Pattern.compile( regex );
	Matcher m = p.matcher( html );

	m.reset();
	boolean result = m.find();
	if ( result ) {
	    StringBuffer sb = new StringBuffer();
	    do {
		String fontSize = m.group( 1 );
		int size = Integer.parseInt( fontSize );

		String replacement = String.format( "font-size: %dpx", size - 14 + 26 );
		m.appendReplacement( sb, replacement );
		result = m.find();
	    } while ( result );
	    m.appendTail( sb );
	    newHtml = sb.toString();
	}

	System.out.println( newHtml );
    }
}
