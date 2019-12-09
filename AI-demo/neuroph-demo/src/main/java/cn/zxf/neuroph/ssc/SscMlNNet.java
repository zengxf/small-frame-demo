package cn.zxf.neuroph.ssc;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.util.TransferFunctionType;

import cn.zxf.neuroph.ssc.common.data_set.DataSetVo;
import lombok.Builder;

@Builder
public class SscMlNNet {

    int                   maxIterations;
    int[]                 levelNeurons;
    // TransferFunctionType inputTransferFunction;
    TransferFunctionType  hideTransferFunction;
    TransferFunctionType  outputTransferFunction;
    // WeightsRandomizer randomizer;
    // --------
    LearningEventListener listener;
    ResultJudger          judger;
    DataSetVo             dataSet;
    // ------------------
    MlPerceptron          nn;

    public SscMlNNet init() {
        nn = new MlPerceptron( hideTransferFunction, outputTransferFunction, levelNeurons );
        nn.getLearningRule().setMaxError( 0.01d );
        nn.getLearningRule().setMaxIterations( maxIterations );
        nn.getLearningRule().addListener( listener );
        return this;
    }

    public SscMlNNet learn() {
        DataSet trainingSet = new DataSet( levelNeurons[0], levelNeurons[levelNeurons.length - 1] );
        dataSet.trainingSet.forEach( trainingSet::addRow );
        this.nn.learn( trainingSet );
        return this;
    }

    public SscMlNNet test() {
        int count = 0, correctNumber = 0;
        for ( DataSetRow row : dataSet.testSet ) {
            nn.setInput( row.getInput() );
            nn.calculate();
            double[] networkOutput = nn.getOutput();
            count++;
            if ( judger.correct( networkOutput, row.getDesiredOutput() ) ) {
                correctNumber++;
            }
        }
        double rate = correctNumber * 100.0 / count;
        System.out.println( rateDescribe( rate ) + "，判断正确率(%)：" + rate );
        return this;
    }

    static String rateDescribe( double rate ) {
        int v = (int) rate;
        if ( v == 0 )
            return "百无一是";
        return "百里中(" + v + ")";
    }

}
