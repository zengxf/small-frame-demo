package cn.simple.test.new_features.jdk9.jt;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Created by zengxf on 2017/10/10.
 */
public class TestDuration {

    public static void main(String[] arr) {
        Duration compTime = Duration.ofDays(23)
                .plusHours(3)
                .plusMinutes(45)
                .plusSeconds(30);
        System.out.println("Duration: " + compTime);
        System.out.println("------------");

        // dividedBy 返回特定除数在调用该方法的持续时间内发生的次数
        long wholeDays = compTime.dividedBy(Duration.ofDays(1));
        long wholeWeeks = compTime.dividedBy(Duration.ofDays(7));
        long wholeHours = compTime.dividedBy(Duration.ofHours(7));
        System.out.println("Number of whole days: " + wholeDays);
        System.out.println("Number of whole weeks: " + wholeWeeks);
        System.out.println("Number of whole hours: " + wholeHours);
        System.out.println("------------");

        // 分解后的一部分
        System.out.println("toDays(): " + compTime.toDays());
        System.out.println("toDaysPart(): " + compTime.toDaysPart());
        System.out.println("toHours(): " + compTime.toHours());
        System.out.println("toHoursPart(): " + compTime.toHoursPart());
        System.out.println("toMinutes(): " + compTime.toMinutes());
        System.out.println("toMinutesPart(): " + compTime.toMinutesPart());
        System.out.println("------------");

        // 截取持续时间
        System.out.println("Truncated to DAYS: " + compTime.truncatedTo(ChronoUnit.DAYS)); // 小于天数的所有部分将被删除
        System.out.println("Truncated to HOURS: " + compTime.truncatedTo(ChronoUnit.HOURS)); // 小于小时的部分删除掉
        System.out.println("Truncated to MINUTES: " + compTime.truncatedTo(ChronoUnit.MINUTES)); // 小于分钟的部分
        System.out.println("------------");
    }

}
