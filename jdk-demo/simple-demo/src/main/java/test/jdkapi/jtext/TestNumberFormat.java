package test.jdkapi.jtext;

import java.text.NumberFormat;
import java.util.Locale;

public class TestNumberFormat {

    public static void main( String[] args ) {

        // -------
        // 数字格式用于格式化数字到不同的区域和不同格式中
        // -------

        // 使用默认语言环境的数字格式
        System.out.println( NumberFormat.getInstance()
                .format( 321.24f ) ); // 321.24
        // 使用荷兰语言环境格式化数字：
        System.out.println( NumberFormat.getInstance( new Locale( "nl" ) )
                .format( 4032.3f ) ); // 4.032,3
        // 使用德国语言环境格式化数字：
        System.out.println( NumberFormat.getInstance( Locale.GERMANY )
                .format( 4032.3f ) ); // 4.032,3

        // -------
        // 使用区域设置格式化货币
        // -------

        // 使用默认语言环境格式化货币
        System.out.println( NumberFormat.getCurrencyInstance()
                .format( 40324.31f ) ); // ￥40,324.31
        // 美元：
        System.out.println( NumberFormat.getCurrencyInstance( Locale.US )
                .format( 40324.3132f ) ); // $40.324,31
        // 人民币：
        System.out.println( NumberFormat.getCurrencyInstance( Locale.CHINA )
                .format( 40324.3132f ) ); // $40.324,31
    }

}
