package cn.zxf.neuroph.ssc.trial;

import java.util.List;

import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.RangeRandomizer;

import cn.zxf.neuroph.ssc.SscNNet;
import cn.zxf.neuroph.ssc.common.array.IntUtils;
import cn.zxf.neuroph.ssc.common.data.CodeSplitUtils;
import cn.zxf.neuroph.ssc.common.data.CodeSplitVo;
import cn.zxf.neuroph.ssc.common.data.ParamVo;
import cn.zxf.neuroph.ssc.common.data_set.BinaryDataSetUtils;
import cn.zxf.neuroph.ssc.common.data_set.DataSetVo;
import cn.zxf.neuroph.ssc.judger.SimilarJudger;
import cn.zxf.neuroph.ssc.listener.CommonListener;

public class NnPd3_X17_3_6_O7 {

    public static void main( String[] args ) {
        ParamVo param = ParamVo.builder().yyyy( 2017 ).mmMin( 7 ).mmMax( 7 ).build();
        List<CodeSplitVo> data = CodeSplitUtils.find( param );
        DataSetVo dataSet = BinaryDataSetUtils.split( data, 3, 3 );

        SscNNet.builder() //
                .maxIterations( 100 ) //
                .levelNeurons( IntUtils.of( 51, 102, 7 ) ) //
                .inputTransferFunction( TransferFunctionType.LINEAR ) //
                .hideTransferFunction( TransferFunctionType.SIGMOID ) //
                .outputTransferFunction( TransferFunctionType.SIGMOID ) //
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
