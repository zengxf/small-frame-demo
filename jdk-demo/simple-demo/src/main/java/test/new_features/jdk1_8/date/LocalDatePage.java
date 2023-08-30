
package test.new_features.jdk1_8.date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalDatePage {

    public static void main( String[] args ) {
        int pageIndex = 0, pageSize = 5;
        LocalDate old = LocalDate.now().plusDays( 1 );
        log.info( "old: {}", old );

        while ( true ) {
            log.info( "\nindex: {}", pageIndex );
            List<String> list = list( pageIndex, pageSize, old );
            if ( list.isEmpty() )
                break;
            pageIndex++;
            list.forEach( System.out::println );
        }
    }

    static List<String> list( int pageIndex, int pageSize, LocalDate old ) {
        List<String> list = new ArrayList<>();

        LocalDate minOld = old;
        LocalDate tomorrow = LocalDate.now().plusDays( 1 ); // 鏄庡ぉ
        LocalDate start = tomorrow.minusDays( ( pageIndex + 1 ) * pageSize - 1 );
        LocalDate end = tomorrow.minusDays( pageIndex * pageSize );

        if ( minOld.isAfter( LocalDate.now() ) ) {
            minOld = LocalDate.now();
        }
        log.info( "start: {}, end: {}", start, end );
        if ( start.isBefore( minOld ) ) {
            start = minOld;
        }
        log.info( "start: {}, end: {}", start, end );

        long total = tomorrow.toEpochDay() - minOld.toEpochDay() + 1;
        log.info( "total: {}", total );

        long count = end.toEpochDay() - start.toEpochDay();
        // log.info( "count: {}", count );

        for ( long i = count; i >= 0; i-- ) {
            LocalDate date = start.plusDays( i );
            String key = date.toString();
            list.add( key );
        }

        return list;
    }

}
