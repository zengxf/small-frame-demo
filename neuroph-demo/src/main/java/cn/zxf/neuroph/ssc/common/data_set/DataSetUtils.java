package cn.zxf.neuroph.ssc.common.data_set;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.neuroph.core.data.DataSetRow;

import cn.zxf.neuroph.ssc.common.data.CodeSplitVo;

@Deprecated
public class DataSetUtils {

    public static DataSetVo split( List<CodeSplitVo> data, int period, int scale ) {
        DataSetVo vo = new DataSetVo();
        int limit = (int) ( data.size() * ( 1D - 1D / scale ) );
        fillList( data, period, vo.trainingSet, 0, limit );
        fillList( data, period, vo.testSet, limit, data.size() );
        return vo;
    }

    private static void fillList( List<CodeSplitVo> data, int period, List<DataSetRow> list, int start, int end ) {
        for ( int i = period + start; i < end; i++ ) {
            List<CodeSplitVo> preList = IntStream.range( i - period, i ).mapToObj( data::get ).collect( Collectors.toList() );
            CodeSplitVo cur = data.get( i );
            DataSetRow row = new DataSetRow( merge( preList ), cur.output() );
            list.add( row );
        }
    }

    private static double[] merge( List<CodeSplitVo> list ) {
        return list.stream() //
                .flatMapToDouble( vo -> DoubleStream.of( vo.data ) ) //
                .toArray();
    }

}
