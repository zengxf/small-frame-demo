package test.new_features.jdk1_8.jl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMath {
    public static void main( String[] args ) {
        // addExact();
        // subtractExact();
        // multiplyExact();
        // incrementExact();
        // decrementExact();
        // negateExact();
        // toIntExact();
        // System.out.println( Math.log( 0.0001 ) );
        System.out.println( Math.sqrt( 4 ) );
        System.out.println( Math.pow( 4, 2 ) );
        System.out.println( Math.toRadians( 0 ) );
    }

    // to int
    static void toIntExact() {
        long a = 10;
        log.info( "{} => {} ", a, Math.toIntExact( a ) );
        try {
            a = 2147483648L;
            Math.toIntExact( a );
        } catch ( ArithmeticException e ) {
            log.info( "{} => {} to int 溢出!!!", a );
        }
    }

    // 变负
    static void negateExact() {
        int a = 10;
        log.info( "{} => {} ", a, Math.negateExact( a ) );
        try {
            a = Integer.MIN_VALUE;
            Math.negateExact( a );
        } catch ( ArithmeticException e ) {
            log.info( "{} => {} 变负溢出!!!", a );
        }
    }

    // 自减
    static void decrementExact() {
        int a = 10;
        log.info( "{} => {} ", a, Math.decrementExact( a ) );
        try {
            a = Integer.MIN_VALUE;
            Math.decrementExact( a );
        } catch ( ArithmeticException e ) {
            log.info( "{} => {} 自减溢出!!!", a );
        }
    }

    // 自增
    static void incrementExact() {
        int a = 10;
        log.info( "{} => {} ", a, Math.incrementExact( a ) );
        try {
            a = Integer.MAX_VALUE;
            Math.incrementExact( a );
        } catch ( ArithmeticException e ) {
            log.info( "{} => {} 自增溢出!!!", a );
        }
    }

    // 乘法
    static void multiplyExact() {
        int a = 10, b = 10;
        log.info( "{} * {} = {}", a, b, Math.multiplyExact( a, b ) );
        try {
            a = Integer.MAX_VALUE;
            b = 2;
            Math.multiplyExact( a, b );
        } catch ( ArithmeticException e ) {
            log.info( "{} * {} 相乘溢出!!!", a, b );
        }
    }

    // 加法
    static void addExact() {
        int a = 10, b = 10;
        log.info( "{} + {} = {}", a, b, Math.addExact( a, b ) );
        try {
            a = Integer.MAX_VALUE;
            b = 1;
            Math.addExact( a, b );
        } catch ( ArithmeticException e ) {
            log.info( "{} + {} 相加溢出!!!", a, b );
        }
    }

    // 减法
    static void subtractExact() {
        int a = 10, b = 10;
        log.info( "{} - {} = {}", a, b, Math.subtractExact( a, b ) );
        try {
            a = Integer.MIN_VALUE;
            b = 1;
            Math.subtractExact( a, b );
        } catch ( ArithmeticException e ) {
            log.info( "{} - {} 相减溢出!!!", a, b );
        }
    }

}
