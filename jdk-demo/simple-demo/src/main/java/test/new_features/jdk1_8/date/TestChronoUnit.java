package test.new_features.jdk1_8.date;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 测试两个时间之间的时间段
 *
 * <p>
 * Created by zxf on 2017-04-20
 */
public class TestChronoUnit {
    public static void main(String[] args) {
        LocalDate t1 = LocalDate.of(2016, 1, 1);
        LocalDate t2 = LocalDate.of(2017, 3, 1);
        long y = ChronoUnit.YEARS.between(t2, t1);
        long m = ChronoUnit.MONTHS.between(t2, t1);
        System.out.println(y);
        System.out.println(m);
    }
}
