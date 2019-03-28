package cn.zxf.spi.services.impl;

import cn.zxf.spi.services.Printer;

public class GoodPrinter implements Printer {
    public void print() {
        System.out.println( "你是个好人~" );
    }
}
