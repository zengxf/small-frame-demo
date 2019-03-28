package cn.zxf.neuroph.ssc.common.array;

import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class DoubleBinaryUtils {

    public static double[] toBinaryArr( int v, int len ) {
        int fv = 1 << len | v; // 填充
        String str = Integer.toBinaryString( fv );
        String[] arr = str.split( "" );
        return Stream.of( arr ).skip( 1 ).mapToDouble( Double::parseDouble ).toArray();
    }

    public static String toString( double[] binaryArr, int len ) {
        int count = binaryArr.length / len;
        String str = "";
        for ( int i = 0; i < count; i++ ) {
            String bstr = DoubleStream.of( binaryArr ).skip( i * len ).limit( len ) //
                    .boxed().mapToInt( Double::intValue ).mapToObj( v -> v + "" ) //
                    .collect( Collectors.joining( "" ) );
            if ( i > 0 )
                str += ", ";
            str += Integer.valueOf( bstr, 2 );
        }
        return str;
    }

}
