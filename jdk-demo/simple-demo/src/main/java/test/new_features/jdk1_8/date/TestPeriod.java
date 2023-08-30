package test.new_features.jdk1_8.date;

import java.time.LocalDate;
import java.time.Period;

public class TestPeriod {

    public static void main( String[] args ) {
        int days = Period.between( LocalDate.now(), LocalDate.now()
                .minusDays( 3 ) )
                .getDays();
        System.out.println( days );
    }

}
