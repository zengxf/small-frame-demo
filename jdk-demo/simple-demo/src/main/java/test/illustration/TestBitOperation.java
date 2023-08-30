package test.illustration;

/**
 * 测试位运算顺序
 */
public class TestBitOperation {

    public static void main( String[] args ) {
        // testShiftLogic();
        // testRightShift();
        // testLogic();
        // testShift();
        // testReverseCode();
        // testTotalSort();

        // 相当于 << | >> | >>> (v % 32)
        System.out.println( Integer.toBinaryString( 1 << 34 ) );
        System.out.println( ( -1024 >> 4 ) );
        System.out.println( Integer.toBinaryString( -1024 ) );
        System.out.println( Integer.toBinaryString( -1024 >> 36 ) );
        System.out.println( Integer.toBinaryString( -1024 >> 4 ) ); // 右移完后，再从 0 | 1 填充左边的
        System.out.println( ( -1 >>> 40 ) );
        System.out.println( Integer.toBinaryString( -1 >>> 40 ) );
        System.out.println( Integer.toBinaryString( -1 >>> 8 ) );
    }

    /** 先 "!" 再 "&&" 然后 "||" */
    static void testBoolean() {
        boolean a = false;
        boolean b = true;
        System.out.println( !a && b || false );
    }

    /** 先位移(左、右、无符号右位移)再逻辑操作(或|、与&、异或^) */
    static void testShiftLogic() {
        int a = 0B111;
        System.out.println( Integer.toBinaryString( a ^ 0B111 << 1 ) );
        System.out.println( Integer.toBinaryString( a ^ 0B111 >> 1 ) );
        System.out.println( Integer.toBinaryString( a ^ 0B111 >>> 1 ) );
    }

    /** 无符号右移，不保留符号头 */
    static void testRightShift() {
        int a = -1;
        System.out.println( Integer.toBinaryString( a >> 31 ) );
        System.out.println( Integer.toBinaryString( a >> 31 )
                .length() );
        System.out.println( Integer.toBinaryString( a >>> 31 ) );
        System.out.println( Integer.toBinaryString( a >>> 31 )
                .length() );
    }

    /** 逻辑顺序：先 & 再 ^ 后 | */
    static void testLogic() {
        System.out.println( Integer.toBinaryString( 0B111 & 0B101 | 0B100 ) ); // 先 & 后 |
        System.out.println( Integer.toBinaryString( 0B111 ^ 0B101 | 0B100 ) ); // 先 ^ 后 |
        System.out.println( Integer.toBinaryString( 0B1111 ^ 0B101 & 0B1101 | 0B100 ) ); // 先 & 再 ^ 后 |
        System.out.println( Integer.toBinaryString( 0B101 & 0B1101 ) ); // 0101
        System.out.println( Integer.toBinaryString( 0B1111 ^ 0B0101 ) ); // 1010
        System.out.println( Integer.toBinaryString( 0B1010 | 0B100 ) ); // 1110
    }

    /** 位移顺序：<<、>>、>>> 按写的顺序 */
    static void testShift() {
        System.out.println( Integer.toBinaryString( 0B111 >> 1 << 2 ) ); // 11 1100
        System.out.println( Integer.toBinaryString( 0B111 << 2 >> 1 ) ); // 11100 1110
        System.out.println( Integer.toBinaryString( 0B111 << 2 >>> 1 << 2 >> 1 ) ); // 11100 1110 111000 11100
    }

    /** 反码：符号位也会反过来 */
    static void testReverseCode() {
        System.out.println( Integer.toBinaryString( ~0B111 ) );
        System.out.println( Integer.toBinaryString( ~0B111 )
                .length() );
        System.out.println( Integer.toBinaryString( ~-0B111 ) );
        System.out.println( Integer.toBinaryString( ~-0B111 )
                .length() );
    }

    /** 总顺序：先正负，再反码，然后位移，最后逻辑 */
    static void testTotalSort() {
        System.out.println( Integer.toBinaryString( ~0B101 & 0B1111 >> 1 ) );
        System.out.println( Integer.toBinaryString( ~0B101 ) ); // 1)
        System.out.println( Integer.toBinaryString( 0B1111 >> 1 ) ); // 2)
        System.out.println( Integer.toBinaryString( ~0B101 & 0B111 ) ); // 3)
    }

}
