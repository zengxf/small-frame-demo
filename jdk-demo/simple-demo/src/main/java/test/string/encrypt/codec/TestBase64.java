package test.string.encrypt.codec;

import java.io.IOException;
import java.util.Base64;

public class TestBase64 {
    public static void main( String[] args ) throws IOException {
        String str = "test input 测试";

        String enc = Base64.getEncoder()
                .encodeToString( str.getBytes() );
        System.out.println( enc );

        String dec = new String( Base64.getDecoder()
                .decode( enc ) );
        System.out.println( dec );

        String enc2 = Base64.getEncoder()
                .withoutPadding()
                .encodeToString( str.getBytes() );
        System.out.println( enc2 );

        String dec2 = new String( Base64.getDecoder()
                .decode( enc2 ) );
        System.out.println( dec2 );

        String url = "https://www.google.co.nz/?gfe_rd=cr&ei=dzbFV&gws_rd=ssl%20dss#q=java中排班表";
        String encUrl = Base64.getUrlEncoder() // 将 '+', '/' 改成 '-', '_'
                .encodeToString( url.getBytes() );
        System.out.println( encUrl );
    }
}
