package cn.test;

import cn.test.utils.StrUtils;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * <p/>
 * Created by ZXFeng on 2024/10/9
 */
public class TestMain {

    public static void main(String[] args) {
        System.out.println("测试 123");
        System.out.println("测试 " + LocalDate.now());
        System.out.println("测试 " + LocalTime.now());

        System.out.println(StrUtils.outMsg());
    }

}
