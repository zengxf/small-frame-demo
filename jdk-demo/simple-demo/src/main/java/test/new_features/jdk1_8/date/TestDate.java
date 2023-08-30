package test.new_features.jdk1_8.date;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class TestDate {
    public static void main( String[] args ) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus( 1, ChronoUnit.DAYS );
        System.out.println( today.until( tomorrow, ChronoUnit.DAYS ) );
    }

    static void test0() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus( 1, ChronoUnit.DAYS );
        LocalDate yesterday = tomorrow.minusDays( 2 );
        System.out.println( yesterday );
        LocalDate independenceDay = LocalDate.of( 2014, Month.JULY, 4 );
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println( dayOfWeek );

        DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate( FormatStyle.MEDIUM ) //
                .withLocale( Locale.GERMAN );
        LocalDate xmas = LocalDate.parse( "24.12.2014", germanFormatter );
        System.out.println( xmas );
    }

    static void test1() {
        Clock clock = Clock.systemDefaultZone();

        long millis = clock.millis();
        long millis2 = System.currentTimeMillis();
        System.out.println( millis );
        System.out.println( millis2 );

        Instant instant = clock.instant();
        Date legacyDate = Date.from( instant ); // legacy java.util.Date
        System.out.println( String.format( "%tF %<tT", legacyDate ) );

        LocalTime late = LocalTime.of( 23, 59, 59 );
        System.out.println( late );

        LocalDate today = LocalDate.now();
        System.out.println( today );

        LocalDateTime date = LocalDateTime.of( 2014, Month.DECEMBER, 31, 23, 59, 59 );
        System.out.println( date );

        System.out.println( IsoChronology.INSTANCE.isLeapYear( 2016 ) ); // 判断是不是闰年
    }
}
