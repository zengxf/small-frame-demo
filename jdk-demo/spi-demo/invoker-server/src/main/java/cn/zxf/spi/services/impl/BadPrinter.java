package cn.zxf.spi.services.impl;

import cn.zxf.spi.services.Printer;

public class BadPrinter implements Printer {
    public void print() {
        System.out.println( "我抽烟，喝酒，蹦迪，但我知道我是好女孩~" );
    }
}