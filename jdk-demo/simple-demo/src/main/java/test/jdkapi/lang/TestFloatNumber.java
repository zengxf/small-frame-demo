package test.jdkapi.lang;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 测试浮点数 <br>
 * 
 * <pre>
 * IEEE 754：(-1)^s * 2^E * M;
 * s: 正数(0)，负数(1)；M: [1, 2)；E: 表示指数位
 * 32位：最高位是符号位s，接着8位是指数E，剩下的23位为有效数字M
 *   s EEEEEEEE MMMMMMMMMMMMMMMMMMMMMMM
 * 64位：最高位是符号位s，接着11位是指数E，剩下的52位为有效数字M
 *   s EEEEEEEEEEE MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 * 1≤M<2，M可以写成1.xxxxxx的形式，其中xxxxxx表示小数部分，只保存后面的xxxxxx部分。读时，再加1
 * E的真实值必须再加上一个中间数：8位(是127)；11位(是1023)。读时再减中间数。—— E有0有1时
 *   E全为1：M全为0，±无穷大；M不全为0，NaN
 *   E全为0: 指数E等于1-127（或者1-1023），有效数字M不再加上第一位的1，而是还原为0.xxxxxx的小数，可以表示±0或很小的数
 * </pre>
 * 
 * 示例：
 * 
 * <pre>
 * 简单 =>：浮点数9.0等于二进制的1001.0，即1.001×2^3
 *   符号位s=0
 *   指数E等于3+127=130，即10000010
 *   有效数字M等于001后面再加20个0（是直接转成小数，所以是在后面追加）
 * </pre>
 * 
 * 转换到二进制：
 * 
 * <pre>
 * 科学计数法表示应该为：将十进制的小数转换为二进制的小数的方法为将小数*2，取整数部分
 *   0.2*2=0.4，第一位为0，0.4的整数部分0
 *   0.4*2=0.8，第二位为0，
 *   0.8*2=1.6，第三位为1，
 *   0.6*2=1.2，第四位为1，
 *   0.2*2=0.4，第五位为0。
 *   相当于是 0.5/n 的才能有限，验证：见{@link #validate()}
 * 9.125F 为例：见{@link #illustration_9_125F()}
 * 209.125F 为例：见{@link #illustration_209_125F()}
 * </pre>
 * 
 * 转换到十进制：
 * 
 * <pre>
 * 示例代码：{@link #illustrationTo_209_125F()}
 * 0 10000110 10100010010000000000000
 *   0: s 即为 0
 *   10000110: 134, 134-127 -> 7
 *   10100010010000000000000：
 *     + 1 // 记得加 1
 *     + 1/2^1
 *     + 1/2^3
 *     + 1/2^7
 *     + 1/2^10
 * </pre>
 * 
 * 总结：转的时候，一除一乘；还原的时候，一乘一除
 * 
 * <p>
 * Created by zengxf on 2018-03-08
 */
public class TestFloatNumber {

    static float v
    // = 9.125F;
            = 209.125F;
    // = 209.125F;
    // = 2.125F;

    public static void main( String[] args ) {
        // testBitsToFloat();
        // testBitsToDouble();
        // testFloatBinary();
        // testDoubleBinary();

        // illustration_209_125F();
        illustrationTo_209_125F();
    }

    static void testBitsToFloat() {
        int bits = Float.floatToIntBits( v );
        float nv = Float.intBitsToFloat( bits );
        System.out.println( bits + "\t" + nv );
    }

    static void testBitsToDouble() {
        long bits = Double.doubleToLongBits( v );
        double nv = Double.longBitsToDouble( bits );
        System.out.println( bits + "\t" + nv );
    }

    static void testFloatBinary() {
        int bits = Float.floatToIntBits( v );
        String binary = Integer.toBinaryString( bits );
        String fill = fill( 32, binary );
        System.out.printf( "%s %s %s%n", fill.charAt( 0 ), fill.substring( 1, 9 ), fill.substring( 9 ) );
    }

    // 转换成二进制
    static void testDoubleBinary() {
        long bits = Double.doubleToLongBits( v );
        String binary = Long.toBinaryString( bits );
        String fill = fill( 64, binary );
        System.out.printf( "%s %s %s%n", fill.charAt( 0 ), fill.substring( 1, 12 ), fill.substring( 12 ) );
    }

    static String fill( int totalLen, String binary ) {
        int len = binary.length();
        String fill = IntStream.range( 0, totalLen - len )
                .mapToObj( i -> "0" )
                .collect( Collectors.joining( "" ) );
        binary = fill + binary;
        return binary;
    }

    // 验证只有 0.5/n 的才为有限位
    static void validate() {
        IntStream.range( 0, 10 )
                .forEach( i -> {
                    System.out.print( "\n" + i + ": " );
                    int temp = 0;
                    for ( int k = 1; k < 11; k++ ) {
                        if ( k == 1 )
                            temp = i * 2;
                        else
                            temp = temp * 2;
                        temp %= 10;
                        System.out.print( " " + temp );
                    }
                } );
    }

    /** 示例：9.125F 的转换 */
    static void illustration_9_125F() {
        System.out.println( 9.125D / 2 );
        System.out.println( 9.125D / 4 );
        System.out.println( 9.125D / 8 ); // 3：除了 3 次 2，直到个位为 1 或为 0。下面的表示乘以 2 的个位是什么
        System.out.println( 0.140625D * 2 ); // 0
        System.out.println( 0.140625D * 4 ); // 0
        System.out.println( 0.140625D * 8 ); // 1
        System.out.println( 0.125D * 2 ); // 0
        System.out.println( 0.125D * 4 ); // 0
        System.out.println( 0.125D * 8 ); // 1
    }

    /** 示例：209.125F 的转换 */
    static void illustration_209_125F() {
        System.out.println( 209.125D / 2 );
        System.out.println( 209.125D / 4 );
        System.out.println( 209.125D / 8 );
        System.out.println( 209.125D / 16 );
        System.out.println( 209.125D / 32 );
        System.out.println( 209.125D / 64 );
        System.out.println( 209.125D / 128 ); // 7：除了 7 次 2
        System.out.println( "----" );
        System.out.println( 0.6337890625D * 2 ); // 1
        System.out.println( 0.267578125D * 2 ); // 0
        System.out.println( 0.53515625D * 2 ); // 1
        System.out.println( 0.0703125D * 2 ); // 0
        System.out.println( 0.0703125D * 4 ); // 0
        System.out.println( 0.0703125D * 8 ); // 0
        System.out.println( 0.0703125D * 16 ); // 1
        System.out.println( 0.125D * 2 ); // 0
        System.out.println( 0.125D * 4 ); // 0
        System.out.println( 0.125D * 8 ); // 1
    }

    /** 示例：还原为 209.125F */
    static void illustrationTo_209_125F() {
        // 0 10000110 10100010010000000000000
        int s = 0;
        int E = Integer.valueOf( "10000110", 2 ) - 127;
        double M = 1.0D; // 10100010010000000000000
        M += 1D / ( 1 << 1 ); // 1，第一个 1
        M += 1D / ( 1 << 3 ); // 101 的 1，第三个 1
        M += 1D / ( 1 << 7 ); // 1010001 的 1，第七个 1
        M += 1D / ( 1 << 10 ); // 1010001001 的 1，第十个 1
        System.out.println( "s: " + s );
        System.out.println( "E: " + E );
        System.out.println( "M: " + M );
        int base = (int) Math.pow( -1, s );
        double nv = base * Math.pow( 2, E ) * M; // -1^s * 2^E * m
        System.out.println( nv );
    }

}
