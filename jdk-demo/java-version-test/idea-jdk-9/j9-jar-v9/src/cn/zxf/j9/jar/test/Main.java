package cn.zxf.j9.jar.test;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Created by zengxf on 2017/10/11.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Inside JDK 9 version of Main.main()...");
        TimeUtil t = new TimeUtil();
        LocalDate ld = t.getLocalDate(Instant.now());
        System.out.println("Local Date: " + ld);
    }

}
