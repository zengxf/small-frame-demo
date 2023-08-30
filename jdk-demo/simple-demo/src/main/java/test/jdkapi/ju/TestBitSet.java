package test.jdkapi.ju;

import java.util.BitSet;

/**
 * 类似 bitmap 算法实现
 * <p>
 * Created by zengxf on 2018-10-18
 */
public class TestBitSet {

    public static void main( String[] args ) {
        BitSet bs = new BitSet();
        bs.set( 9 );
        bs.set( 10 );
        bs.set( 100 );
        System.out.println( bs.get( 8 ) );
        System.out.println( bs.get( 9 ) );
        System.out.println( bs.get( 10 ) );
        System.out.println( bs.get( 100 ) );
        System.out.println( bs.get( 11 ) );
        System.out.println( Integer.toBinaryString( 1 << 34 ) );
    }

}
