package test.jdkapi.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TestLocalDate {
    public static void main( String[] args ) {
        long ts = System.currentTimeMillis();
        long localEpochDay = ts / 1000 / 86400;
        LocalDate date = LocalDate.ofEpochDay( localEpochDay );
        System.out.println( date );

        date = Instant.ofEpochMilli( ts )
                .atZone( ZoneId.systemDefault() )
                .toLocalDate();
        System.out.println( date );
    }
}
