package cn.zxf.neuroph.ssc.trial;

import java.util.List;

import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.RangeRandomizer;

import cn.zxf.neuroph.ssc.SscNNet;
import cn.zxf.neuroph.ssc.common.array.IntUtils;
import cn.zxf.neuroph.ssc.common.data.CodeSplitUtils;
import cn.zxf.neuroph.ssc.common.data.CodeSplitVo;
import cn.zxf.neuroph.ssc.common.data.ParamVo;
import cn.zxf.neuroph.ssc.common.data_set.DataSetUtils;
import cn.zxf.neuroph.ssc.common.data_set.DataSetVo;
import cn.zxf.neuroph.ssc.judger.SimilarJudger;
import cn.zxf.neuroph.ssc.listener.CommonListener;

@Deprecated // 溢出
public class NnPd3_X5_3_6_O2 {

    public static void main( String[] args ) {
        ParamVo param = ParamVo.builder().yyyy( 2017 ).mmMin( 7 ).mmMax( 7 ).build();
        List<CodeSplitVo> data = CodeSplitUtils.find( param );
        DataSetVo dataSet = DataSetUtils.split( data, 3, 3 );

        SscNNet.builder() //
                .maxIterations( 100 ) //
                .levelNeurons( IntUtils.of( 15, 30, 2 ) ) //
                .inputTransferFunction( TransferFunctionType.LINEAR ) //
                .hideTransferFunction( TransferFunctionType.SIGMOID ) //
                .outputTransferFunction( TransferFunctionType.LINEAR ) //
                .randomizer( new RangeRandomizer( 0, 1 ) ) //
                // -------
                .judger( SimilarJudger.INSTANCE ) //
                .listener( CommonListener.INSTANCE ) //
                .dataSet( dataSet ) //
                // -------
                .build()
                // -------
                .init() //
                .learn() //
                .test();

    }

}
