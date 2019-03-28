package cn.zxf.spi.test;

import java.util.ServiceLoader;

import cn.zxf.spi.services.Printer;

public class SpiTest {

    public static void main( String[] args ) {
        ServiceLoader<Printer> loader = ServiceLoader.load( Printer.class );
        for ( Printer printer : loader ) {
            printer.print();
        }
    }

}
