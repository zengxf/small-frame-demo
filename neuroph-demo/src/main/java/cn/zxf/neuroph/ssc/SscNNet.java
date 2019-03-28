package cn.zxf.neuroph.ssc;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.LayerFactory;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.NeuralNetworkType;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.WeightsRandomizer;

import cn.zxf.neuroph.ssc.common.data_set.DataSetVo;
import lombok.Builder;

@Builder
public class SscNNet {

    int                            maxIterations;
    int[]                          levelNeurons;
    TransferFunctionType           inputTransferFunction;
    TransferFunctionType           hideTransferFunction;
    TransferFunctionType           outputTransferFunction;
    WeightsRandomizer              randomizer;
    // --------
    LearningEventListener          listener;
    ResultJudger                   judger;
    DataSetVo                      dataSet;
    // ------------------
    NeuralNetwork<BackPropagation> nn;

    @SuppressWarnings( "unchecked" )
    public SscNNet init() {
        nn = new NeuralNetwork<>();

        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty( "transferFunction", hideTransferFunction );
        nn.setNetworkType( NeuralNetworkType.MULTI_LAYER_PERCEPTRON );

        NeuronProperties inputNeuronProperties = new NeuronProperties( InputNeuron.class, inputTransferFunction.getTypeClass() );
        Layer layer = LayerFactory.createLayer( levelNeurons[0], inputNeuronProperties );
        layer.addNeuron( new BiasNeuron() );
        nn.addLayer( layer );

        // 创建隐层
        Layer prevLayer = layer;
        for ( int layerIdx = 1; layerIdx < levelNeurons.length; layerIdx++ ) {
            int neuronsNum = levelNeurons[layerIdx];
            if ( layerIdx == levelNeurons.length - 1 ) {
                neuronProperties.setProperty( "transferFunction", outputTransferFunction.getTypeClass() );
            }
            layer = LayerFactory.createLayer( neuronsNum, neuronProperties );
            nn.addLayer( layer );
            // 最后一层不需要偏置
            if ( layerIdx != levelNeurons.length - 1 )
                layer.addNeuron( new BiasNeuron() );
            // 连接各层
            if ( prevLayer != null ) {
                ConnectionFactory.fullConnect( prevLayer, layer );
            }
            prevLayer = layer;
        }

        NeuralNetworkFactory.setDefaultIO( nn );
        nn.setLearningRule( this.rule() );
        nn.randomizeWeights( randomizer );

        return this;
    }

    public SscNNet learn() {
        DataSet trainingSet = new DataSet( levelNeurons[0], levelNeurons[levelNeurons.length - 1] );
        dataSet.trainingSet.forEach( trainingSet::addRow );
        this.nn.learn( trainingSet );
        return this;
    }

    public SscNNet test() {
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

    BackPropagation rule() {
        BackPropagation rule = new BackPropagation();
        rule.setMaxError( 0.01d );
        rule.setMaxIterations( maxIterations );
        rule.addListener( listener );
        return rule;
    }

}
