package cn.simple.test.new_features.jdk9.jvm.jtools;

import java.time.LocalTime;

/**
 * Created by zengxf on 2017/10/12.
 */
public class TestJToolLoop {

    public static void main(String[] arr) throws InterruptedException {
        while (true) {
            System.out.println(LocalTime.now());
            Thread.sleep(1000L);
        }
    }

}
