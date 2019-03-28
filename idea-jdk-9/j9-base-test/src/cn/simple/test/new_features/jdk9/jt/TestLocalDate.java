package cn.simple.test.new_features.jdk9.jt;

import java.time.*;
import java.util.Date;

/**
 * Created by zengxf on 2017/10/10.
 */
public class TestLocalDate {

    public static void main(String[] arr) {
//        ofInstant();
        datesUntil();
    }

    static void datesUntil() {
        LocalDate.of(2017, 10, 1)
                .datesUntil(LocalDate.of(2017, 12, 1))
                .forEach(System.out::println);
        System.out.println("------------------");
        LocalDate.of(2017, 10, 1)
                .datesUntil(LocalDate.of(2017, 12, 1), Period.ofWeeks(1))
                .forEach(System.out::println);
        System.out.println("------------------");
        System.out.println("Last Day of months in 2017:");
        LocalDate.of(2017, 1, 31)
                .datesUntil(LocalDate.of(2018, 1, 1), Period.ofMonths(1))
                .forEach(System.out::println);
    }

    static void ofInstant() {
        Date dt = new Date();

        // In JDK 8
        LocalDate ld = dt.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        System.out.println("Current Local Date: " + ld);

        // In JDK 9
        LocalDate ld2 = LocalDate.ofInstant(dt.toInstant(), ZoneId.systemDefault());
        System.out.println("Current Local Date: " + ld2);


        Instant now = Instant.now();
        ZoneId zone = ZoneId.systemDefault();

        // In JDK 8
        ZonedDateTime zdt = now.atZone(zone);
        LocalDate ld3 = zdt.toLocalDate();
        LocalTime lt3 = zdt.toLocalTime();
        System.out.println("Local Date: " + ld3 + ", Local Time:" + lt3);

        // In JDK 9
        LocalDate ld4 = LocalDate.ofInstant(now, zone);
        LocalTime lt4 = LocalTime.ofInstant(now, zone);
        System.out.println("Local Date: " + ld4 + ", Local Time:" + lt4);
    }

}
