package cn.zxf.j9.service.impl;

import cn.zxf.j9.service.PrintService;

/**
 * Created by zengxf on 2017/10/11.
 */
public class SayPrintService implements PrintService {

    @Override
    public void print() {
        System.out.println("say hello ....");
    }
}
