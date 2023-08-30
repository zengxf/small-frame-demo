package test.new_features.jdk1_8.date;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;

public class TestDuration {
    public static void main(String[] args) {
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
        LocalDateTime tomorrow = sylvester.plusDays(1);
        Duration d1 = Duration.between(sylvester, tomorrow);
        System.out.println(d1.toDays());

        Period tenDays = Period.between(
                LocalDate.of(2014, 3, 8),
                LocalDate.of(2014, 3, 18)
        );
        System.out.println(tenDays.getDays());
    }
}
