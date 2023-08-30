package test.new_features.jdk1_8.date;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.util.Locale;

public class TestZoneOffset {
    public static void main( String[] args ) {
        ZoneOffset newYorkOffset = ZoneOffset.of( "-05:00" );
        LocalDateTime dateTime = LocalDateTime.of( 2014, Month.MARCH, 18, 13, 45, 54 );
        OffsetDateTime dateTimeInNewYork = OffsetDateTime.of( dateTime, newYorkOffset );

        System.out.println( dateTime );
        System.out.println( dateTimeInNewYork );

        Chronology japaneseChronology = Chronology.ofLocale( Locale.JAPAN );
        ChronoLocalDate now = japaneseChronology.dateNow();
        System.out.println( now );

    }
}
