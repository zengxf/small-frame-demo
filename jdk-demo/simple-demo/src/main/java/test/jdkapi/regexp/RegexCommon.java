package test.jdkapi.regexp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCommon {
    public static void main( String[] args ) throws IOException {
        test2();
    }

    public static void test2() throws IOException {
        // String str = "=?GBK?Q?test=D6=D0 =CE=C4.zip?=";
        String str = "=?gbk?B?dGVzdCBlbi50eHQ=?=";
        String regex = "=\\?(\\w+)\\?(\\w+)\\?(.+?)\\?=";

        Pattern plainPattern = Pattern.compile( regex );
        Matcher plainMatcher = plainPattern.matcher( str );

        if ( plainMatcher.find() ) {
            String charset = plainMatcher.group( 1 );
            String encodeType = plainMatcher.group( 2 );
            String fileName = plainMatcher.group( 3 );

            if ( "Q".equals( encodeType ) ) {
                fileName = getCommonDecode( fileName, charset );
            } else if ( "B".equals( encodeType ) ) {
                fileName = getBase64Decode( fileName, charset );
            } else {

            }

            System.out.println( fileName );
        }

    }

    /**
     * Base64方式解码
     * 
     * @param old
     * @param charset
     * @return
     * @throws IOException
     */
    public static String getBase64Decode( String old, String charset ) throws IOException {
        byte[] arr = Base64.getDecoder().decode( old );
        String newStr = new String( arr, charset );
        return newStr;
    }

    /**
     * 常规方式解码
     * 
     * @param old
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getCommonDecode( String old, String charset ) throws UnsupportedEncodingException {
        String newStr = old;

        String charRegex = "((=\\w{2})+)";

        Pattern charPattern = Pattern.compile( charRegex );
        Matcher charMatcher = charPattern.matcher( old );

        boolean result = charMatcher.find();
        if ( result ) {
            StringBuffer sb = new StringBuffer();
            do {
                String target = charMatcher.group( 1 );
                String replacement = getTransform( target, charset );
                charMatcher.appendReplacement( sb, replacement );
                result = charMatcher.find();
            } while ( result );
            charMatcher.appendTail( sb );
            newStr = sb.toString();
        }

        return newStr;
    }

    /**
     * 将“=D6=D0=CE=C4”转换成“中文”
     * 
     * @param old
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getTransform( String old, String charset ) throws UnsupportedEncodingException {
        String[] chars = old.replaceFirst( "=", "" ).split( "=" );
        byte[] arr = new byte[chars.length];
        for ( int i = 0; i < chars.length; i++ ) {
            arr[i] = Integer.valueOf( chars[i], 16 ).byteValue();
        }
        return new String( arr, charset );
    }

    public static void test1() {
        String regex = "TEXT/PLAIN; name=\"(.+)\"";
        String str = "TEXT/PLAIN; name=\"=?GBK?Q?testbb=D6=D0=CE=C4bb.txt?=\"";

        Pattern plainPattern = Pattern.compile( regex );
        Matcher plainMatcher = plainPattern.matcher( str );

        if ( plainMatcher.find() ) {
            System.out.println( plainMatcher.group( 1 ) );
        }
    }
}
