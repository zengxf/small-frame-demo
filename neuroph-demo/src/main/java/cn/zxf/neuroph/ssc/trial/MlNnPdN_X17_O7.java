package cn.zxf.neuroph.ssc.trial;

import java.util.List;

import org.neuroph.util.TransferFunctionType;

import cn.zxf.neuroph.ssc.SscMlNNet;
import cn.zxf.neuroph.ssc.common.array.IntUtils;
import cn.zxf.neuroph.ssc.common.data.CodeSplitUtils;
import cn.zxf.neuroph.ssc.common.data.CodeSplitVo;
import cn.zxf.neuroph.ssc.common.data.ParamVo;
import cn.zxf.neuroph.ssc.common.data_set.BinaryDataSetUtils;
import cn.zxf.neuroph.ssc.common.data_set.DataSetVo;
import cn.zxf.neuroph.ssc.judger.SimilarJudger;
import cn.zxf.neuroph.ssc.listener.CommonListener;

public class MlNnPdN_X17_O7 {

    static final int PD = 15 //
            , SCALE = 4;

    public static void main( String[] args ) {
        ParamVo param = ParamVo.builder().yyyy( 2017 ).mmMin( 7 ).mmMax( 8 ).build();
        List<CodeSplitVo> data = CodeSplitUtils.find( param );
        DataSetVo dataSet = BinaryDataSetUtils.split( data, PD, SCALE );

        SscMlNNet.builder() //
                .maxIterations( 20 ) //
                .levelNeurons( IntUtils.of( PD * 17, PD * 17, 7 ) ) //
                // .inputTransferFunction( TransferFunctionType.LINEAR ) //
                .hideTransferFunction( TransferFunctionType.SIGMOID ) //
                .outputTransferFunction( TransferFunctionType.SIGMOID ) //
                // .randomizer( new RangeRandomizer( 0, 1 ) ) //
                // -------
                // .judger( EqualJudger.INSTANCE ) //
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
