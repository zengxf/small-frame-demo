
package cn.zxf.neuroph.ssc;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.LayerFactory;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.NeuralNetworkType;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.NguyenWidrowRandomizer;

/**
 * BP神经网络
 */
public class MlPerceptron extends NeuralNetwork<BackPropagation> {

    private static final long    serialVersionUID = 2L;

    private TransferFunctionType outputTransferFunction;

    public MlPerceptron( List<Integer> neuronsInLayers ) {
        // 初始化神经元设置
        NeuronProperties neuronProperties = new NeuronProperties();
        // 设置为SIGMOID传输函数
        neuronProperties.setProperty( "transferFunction", TransferFunctionType.SIGMOID );
        this.createNetwork( neuronsInLayers, neuronProperties );
    }

    public MlPerceptron( int... neuronsInLayers ) {
        NeuronProperties neuronProperties = new NeuronProperties();
        // 设置为SIGMOID传输函数
        neuronProperties.setProperty( "transferFunction", TransferFunctionType.SIGMOID );
        List<Integer> neuronsInLayersVector = new ArrayList<Integer>();
        for ( int i = 0; i < neuronsInLayers.length; i++ ) {
            neuronsInLayersVector.add( new Integer( neuronsInLayers[i] ) );
        }
        this.createNetwork( neuronsInLayersVector, neuronProperties );
    }

    public MlPerceptron( TransferFunctionType transferFunctionType, TransferFunctionType outputTransferFunction, int... neuronsInLayers ) {
        this.outputTransferFunction = outputTransferFunction;
        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty( "transferFunction", transferFunctionType );
        List<Integer> neuronsInLayersVector = new ArrayList<Integer>();
        for ( int i = 0; i < neuronsInLayers.length; i++ ) {
            neuronsInLayersVector.add( new Integer( neuronsInLayers[i] ) );
        }
        this.createNetwork( neuronsInLayersVector, neuronProperties );
    }

    public MlPerceptron( List<Integer> neuronsInLayers, TransferFunctionType transferFunctionType ) {
        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty( "transferFunction", transferFunctionType );
        this.createNetwork( neuronsInLayers, neuronProperties );
    }

    public MlPerceptron( List<Integer> neuronsInLayers, NeuronProperties neuronProperties ) {
        this.createNetwork( neuronsInLayers, neuronProperties );
    }

    protected void createNetwork( List<Integer> neuronsInLayers, NeuronProperties neuronProperties ) {

        // 设置神经网络类型
        this.setNetworkType( NeuralNetworkType.MULTI_LAYER_PERCEPTRON );

        // 输入神经元
        NeuronProperties inputNeuronProperties = new NeuronProperties( InputNeuron.class, Linear.class );
        Layer layer = LayerFactory.createLayer( neuronsInLayers.get( 0 ), inputNeuronProperties );
        layer.addNeuron( new BiasNeuron() );
        this.addLayer( layer );
        // 创建隐层
        Layer prevLayer = layer;
        for ( int layerIdx = 1; layerIdx < neuronsInLayers.size(); layerIdx++ ) {
            Integer neuronsNum = neuronsInLayers.get( layerIdx );
            if ( layerIdx == neuronsInLayers.size() - 1 ) {
                neuronProperties.setProperty( "transferFunction", outputTransferFunction.getTypeClass() );
            }
            layer = LayerFactory.createLayer( neuronsNum, neuronProperties );
            this.addLayer( layer );
            // 最后一层不需要偏置
            if ( layerIdx != neuronsInLayers.size() - 1 )
                layer.addNeuron( new BiasNeuron() );
            // 连接各层
            if ( prevLayer != null ) {
                ConnectionFactory.fullConnect( prevLayer, layer );
            }

            prevLayer = layer;
        }
        NeuralNetworkFactory.setDefaultIO( this );
        // 设置学习算法，此处为反向传播
        this.setLearningRule( new BackPropagation() );
        // 初始化神经元的连接权值
        this.randomizeWeights( new NguyenWidrowRandomizer( -0.7, 0.7 ) );
    }
}
