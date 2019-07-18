package cn.simple.test.new_features.jdk9.profile;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;

/**
 * <pre>
 *     1) jprofiler
 *     2) xrebel
 *     3) YourKit
 *     4) AQtime
 * </pre>
 * Created by zengxf on 2017/11/24.
 */
public class TestWhile {

    public static void main(String[] arr) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        while (true) {
            System.out.println(LocalDateTime.now());
            Thread.sleep(2000L);
        }
    }

}
