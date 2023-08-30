package test.jdkapi.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class TestLocalDateTime {
    public static void main( String[] args ) {
        long ts = System.currentTimeMillis();
        long epochSecond = ts / 1000;
        int nanoOfSecond = (int) ( ts % 1000 ) * 1000_000;
        LocalDateTime ldt = LocalDateTime.ofEpochSecond( epochSecond, nanoOfSecond, OffsetDateTime.now()
                .getOffset() );

        ldt = Instant.ofEpochMilli( ts )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
        System.out.println( ldt );

        System.out.println( ldt );
        System.out.println( ldt.toLocalDate() );
        System.out.println( ldt.toLocalTime() );

        LocalTime lt = ldt.toLocalTime();
        LocalTime refLt = LocalTime.of( 18, 30 );
        System.out.println( lt.isBefore( refLt ) );
    }
}
