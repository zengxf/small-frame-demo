package test.algorithm.shift_bit;

/**
 * 字节高低位交换
 * 
 * @author zengxf
 */
public class ShiftBit1 {
    public static void main( String[] args ) {
	// ds();
	// zg();
	System.out.println( Integer.toBinaryString( 0xcc ) );
	System.out.println( "00" + Integer.toBinaryString( 0x33 ) );
	System.out.println( Integer.toBinaryString( 0xaa ) );
	System.out.println( "0" + Integer.toBinaryString( 0x55 ) );
    }

    /**
     * 蝶式交换
     */
    static void ds() {
	int data = 0b1101_1001;

	System.out.println( Integer.toBinaryString( data ) );

	data = ( data << 4 ) | ( data >> 4 );
	data = ( ( data << 2 ) & 0xcc ) | ( ( data >> 2 ) & 0x33 );
	data = ( ( data << 1 ) & 0xaa ) | ( ( data >> 1 ) & 0x55 );

	System.out.println( Integer.toBinaryString( data ) );
    }

    /**
     * 逐个交换其高低位
     */
    static void zg() {
	int data = 0b1010_1001;

	System.out.println( Integer.toBinaryString( data ) );

	int tmp = 0x00;
	for ( int i = 0; i < 8; i++ ) {
	    tmp = ( ( data >> i ) & 0x01 ) | tmp;
	    if ( i < 7 )
		tmp = tmp << 1;
	}
	data = tmp;

	System.out.println( Integer.toBinaryString( data ) );
    }

}
