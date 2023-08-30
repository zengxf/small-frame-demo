package test.string;

import java.util.Date;

public class StringFormat {

    public static void main( String[] args ) {
        System.out.println( String.format( "%02d-%02d", 2, 22 ) );

        String v = String.format( "%s %tF %<tT %s %d", ">>", new Date(), "<<>> ", 3 ); // 用 '<' 代替 '1$'
        System.out.println( v );

        v = String.format( "0x%1$02x", 11 ); // 用 0 填充，小写
        System.out.println( v );

        v = String.format( "0x%02X", 11 ); // 用 0 填充，大写
        System.out.println( v );
    }

}
