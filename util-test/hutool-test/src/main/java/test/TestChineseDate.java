package test;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;

public class TestChineseDate {

    public static void main( String[] args ) {
        ChineseDate date = new ChineseDate( DateUtil.parseDate( "2020-01-25" ) );
        // 一月
        System.out.println( date.getChineseMonth() );
        // 正月
        System.out.println( date.getChineseMonthName() );
        // 初一
        System.out.println( date.getChineseDay() );
        // 庚子
        System.out.println( date.getCyclical() );
        // 鼠
        System.out.println( date.getChineseZodiac() );
        // 春节
        System.out.println( "节日：" + date.getFestivals() );
        // 庚子鼠年 正月初一
        System.out.println( date.toString() );
    }

}
