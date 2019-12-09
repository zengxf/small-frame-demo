package cn.zxf.neuroph.ssc.common.data_set;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.neuroph.core.data.DataSetRow;

import cn.zxf.neuroph.ssc.common.array.DoubleBinaryUtils;
import cn.zxf.neuroph.ssc.common.data.CodeSplitVo;

public class BinaryDataSetUtils {

    public static DataSetVo split( List<CodeSplitVo> data, int period, int scale ) {
        DataSetVo vo = new DataSetVo();
        int limit = (int) ( data.size() * ( 1D - 1D / scale ) );
        fillList( data.subList( 0, limit ), period, vo.trainingSet );
        fillList( data.subList( limit, data.size() ), period, vo.testSet );
        return vo;
    }

    private static void fillList( List<CodeSplitVo> data, int period, List<DataSetRow> target ) {
        Map<String, List<CodeSplitVo>> map = data.stream().collect( Collectors.groupingBy( CodeSplitVo::getDate ) );
        map.values().forEach( list -> {
            if ( list.size() < period )
                return;
            Collections.sort( list, Comparator.comparing( CodeSplitVo::getPeriod ) );
            for ( int i = period; i < list.size(); i++ ) {
                List<CodeSplitVo> preList = IntStream.range( i - period, i ).mapToObj( list::get ).collect( Collectors.toList() );
                CodeSplitVo cur = list.get( i );
                DataSetRow row = new DataSetRow( merge( preList ), binary( cur.output() ) );
                target.add( row );
            }
        } );
    }

    private static double[] merge( List<CodeSplitVo> list ) {
        return list.stream() //
                .flatMapToDouble( vo -> DoubleStream.of( binary( vo.data ) ) ) //
                .toArray();
    }

    private static double[] binary( double... arr ) {
        int len = arr.length == 2 ? 7 : arr.length == 5 ? 17 : 0;
        double sum = 0;
        for ( int i = arr.length - 1; i >= 0; i-- ) {
            sum *= 10;
            sum += arr[i];
        }
        int v = (int) sum;
        return DoubleBinaryUtils.toBinaryArr( v, len );
    }

    // public static void main( String[] args ) {
    // System.out.println( Integer.toBinaryString( 99 ) );
    // System.out.println( Arrays.toString( binary( 9, 9 ) ) );
    // System.out.println( Integer.toBinaryString( 99999 ) );
    // System.out.println( Arrays.toString( binary( 9, 9, 9, 9, 9 ) ) );
    // }

}
