package test.new_features.jdk1_8.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class TestFormat {
    public static void main( String[] args ) {
        // fmtYyyyWeek();
        // fmtYyyyMMdd();

        // test1();
        // testLocal();
        // testLocal1();
        // testZone();

        System.out.println( LocalDate.of( 2017, 12, 11 ).get( ChronoField.ALIGNED_WEEK_OF_YEAR ) );
        System.out.println( LocalDate.of( 2017, 1, 1 ).with( ChronoField.ALIGNED_WEEK_OF_YEAR, 51 ).with( ChronoField.DAY_OF_WEEK, 1 ) );
    }

    static void fmtYyyyWeek() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-w" );
        LocalDate date1 = LocalDate.of( 2017, 12, 7 );
        String formattedDate = date1.format( formatter );
        System.out.println( formattedDate );
    }

    static void fmtYyyyMMdd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
        LocalDate date1 = LocalDate.of( 2014, 3, 18 );
        String formattedDate = date1.format( formatter );
        System.out.println( formattedDate );
        LocalDate date2 = LocalDate.parse( formattedDate, formatter );
        System.out.println( date2 );
    }

    static void testZone() {

        System.out.println( ZoneId.systemDefault() );

        ZoneId romeZone = ZoneId.of( "Europe/Rome" );
        LocalDate date = LocalDate.of( 2014, Month.MARCH, 18 );
        ZonedDateTime zdt1 = date.atStartOfDay( romeZone );
        System.out.println( zdt1 );

        LocalDateTime dateTime = LocalDateTime.of( 2014, Month.MARCH, 18, 13, 45 );
        ZonedDateTime zdt2 = dateTime.atZone( romeZone );
        System.out.println( zdt2 );

        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone( romeZone );
        System.out.println( zdt3 );

        ZonedDateTime zdt4 = ZonedDateTime.now();
        System.out.println( zdt4 );
    }

    static void test1() {
        LocalDate date1 = LocalDate.parse( "20140318", DateTimeFormatter.BASIC_ISO_DATE );
        LocalDate date2 = LocalDate.parse( "2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE );
        System.out.println( date1 );
        System.out.println( date2 );
    }

    static void testLocal() {
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern( "dd. MMMM yyyy", Locale.ITALIAN );
        System.out.println( italianFormatter );
        LocalDate date1 = LocalDate.of( 2014, 3, 1 );
        String formattedDate = date1.format( italianFormatter ); // 01. marzo 2014
        System.out.println( formattedDate );
        LocalDate date2 = LocalDate.parse( formattedDate, italianFormatter );
        System.out.println( date2 );
    }

    static void testLocal1() {
        DateTimeFormatter italianFormatter = new DateTimeFormatterBuilder() //
                .appendText( ChronoField.DAY_OF_MONTH ) //
                .appendLiteral( ". " )//
                .appendText( ChronoField.MONTH_OF_YEAR )//
                .appendLiteral( " " )//
                .appendText( ChronoField.YEAR )//
                .parseCaseInsensitive()//
                .toFormatter( Locale.ITALIAN );
        System.out.println( italianFormatter );
        LocalDate date1 = LocalDate.of( 2014, 3, 1 );
        String formattedDate = date1.format( italianFormatter ); // 01. marzo 2014
        System.out.println( formattedDate );
        LocalDate date2 = LocalDate.parse( formattedDate, italianFormatter );
        System.out.println( date2 );
    }

}
