package test.new_features.jdk1_8.date;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TestTime {
    public static void main( String[] args ) {
        LocalTime late = LocalTime.of( 23, 59, 59 );
        System.out.println( late ); // 23:59:59
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedTime( FormatStyle.SHORT )//
                .withLocale( Locale.GERMAN );
        LocalTime leetTime = LocalTime.parse( "13:37", germanFormatter );
        System.out.println( leetTime );
    }
}
