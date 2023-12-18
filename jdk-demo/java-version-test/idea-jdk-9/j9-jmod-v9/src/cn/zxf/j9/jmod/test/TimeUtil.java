package cn.zxf.j9.jmod.test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by zengxf on 2017/10/11.
 */
public class TimeUtil {

    public TimeUtil() {
        System.out.println("Creating JDK 9 version of TimeUtil...");
    }

    public LocalDate getLocalDate(Instant now) {
        return LocalDate.ofInstant(now, ZoneId.systemDefault());
    }

}
