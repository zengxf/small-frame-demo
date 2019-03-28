package cn.zxf.neuroph.ssc.trial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
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

import cn.zxf.neuroph.ssc.common.db.OpenRecordDao;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated // 没归一化，数据累加溢出
public class Nn10_2 implements LearningEventListener {

    static final TransferFunctionType transferFunction = TransferFunctionType.LINEAR;
    static final int[]                neuronsNumArr    = { 5, 2 };                                               // 还要加上输出层
    static final int                  maxIterations    = 10;

    private final OpenRecordDao       dao              = new OpenRecordDao();
    static final String               sqlFmt           = "SELECT n1,n2, n3, n4,n5 "                              //
            + "FROM ssc_open_record "                                                                            //
            // + "WHERE yyyy=2017 AND mm BETWEEN 10 AND 10 AND dd BETWEEN 1 AND 1 ORDER BY yyyy, mm, dd, period";
            + "WHERE yyyy=2017 AND mm = 10 AND dd = 1 AND period BETWEEN 1 AND 20 ORDER BY yyyy, mm, dd, period";

    public static void main( String[] args ) {
        Nn10_2 self = new Nn10_2();
        self.run();
    }

    void run() {
        NeuralNetwork<BackPropagation> nn = nn();
        List<DataSetRow> data = this.data();
        int limit = data.size() / 3;

        DataSet trainingSet = new DataSet( 10, 2 );
        for ( int i = 0; i < limit; i++ ) {
            trainingSet.addRow( data.get( i ) );
        }

        nn.getLearningRule().setMaxError( 0.4d );
        nn.getLearningRule().setMaxIterations( maxIterations );
        nn.getLearningRule().addListener( this );
        nn.learn( trainingSet );

        List<DataSetRow> testSet = new ArrayList<>();
        for ( int i = limit; i < data.size(); i++ ) {
            testSet.add( data.get( i ) );
        }

        this.test( nn, testSet );
    }

    void test( NeuralNetwork<BackPropagation> nn, List<DataSetRow> testSet ) {
        int count = 0, correctNumber = 0;
        for ( DataSetRow row : testSet ) {
            nn.setInput( row.getInput() );
            nn.calculate();
            double[] networkOutput = nn.getOutput();
            count++;
            log.info( "in: {}, out: {}, desired: {}", row.getInput(), networkOutput, row.getDesiredOutput() );
            if ( Arrays.equals( networkOutput, row.getDesiredOutput() ) ) {
                correctNumber++;
            }
        }
        System.out.println( "判断正确率:" + ( correctNumber * 1.0 / count ) * 100 + "%" );
    }

    static NeuralNetwork<BackPropagation> nn() {
        NeuralNetwork<BackPropagation> nn = new NeuralNetwork<>();

        NeuronProperties neuronProperties = new NeuronProperties();
        neuronProperties.setProperty( "transferFunction", transferFunction );
        nn.setNetworkType( NeuralNetworkType.MULTI_LAYER_PERCEPTRON );

        NeuronProperties inputNeuronProperties = new NeuronProperties( InputNeuron.class, Linear.class );
        Layer layer = LayerFactory.createLayer( 10, inputNeuronProperties );
        layer.addNeuron( new BiasNeuron() );
        nn.addLayer( layer );

        // 创建隐层
        Layer prevLayer = layer;
        for ( int layerIdx = 0; layerIdx < neuronsNumArr.length; layerIdx++ ) {
            int neuronsNum = neuronsNumArr[layerIdx];
            layer = LayerFactory.createLayer( neuronsNum, neuronProperties );
            nn.addLayer( layer );
            // 最后一层不需要偏置
            if ( layerIdx != neuronsNumArr.length - 1 )
                layer.addNeuron( new BiasNeuron() );
            // 连接各层
            if ( prevLayer != null ) {
                ConnectionFactory.fullConnect( prevLayer, layer );
            }
            prevLayer = layer;
        }

        NeuralNetworkFactory.setDefaultIO( nn );
        nn.setLearningRule( new BackPropagation() );
        nn.randomizeWeights( new NguyenWidrowRandomizer( -0.7, 0.7 ) );

        return nn;
    }

    List<DataSetRow> data() {
        List<DataSetRow> list = new ArrayList<>();
        String sql = String.format( sqlFmt );
        List<Row> rows = dao.find( sql, res -> {
            try {
                return Row.builder()//
                        .n1( res.getDouble( "n1" ) )//
                        .n2( res.getDouble( "n2" ) )//
                        .n3( res.getDouble( "n3" ) )//
                        .n4( res.getDouble( "n4" ) )//
                        .n5( res.getDouble( "n5" ) )//
                        .build();
            } catch ( SQLException e ) {
                throw new RuntimeException( "解析数据出错！", e );
            }
        } );
        for ( int i = 2; i < rows.size(); i++ ) {
            Row in1 = rows.get( i - 2 );
            Row in2 = rows.get( i - 1 );
            Row out = rows.get( i );
            DataSetRow row = new DataSetRow();
            row.setInput( new double[] { //
                    in1.n1, in1.n2, in1.n3, in1.n4, in1.n5, //
                    in2.n1, in2.n2, in2.n3, in2.n4, in2.n5 } );
            row.setDesiredOutput( new double[] { out.n4, out.n5 } );
            list.add( row );
        }
        return list;
    }

    @Builder
    public static class Row {
        double n1, n2, n3, n4, n5;
    }

    @Override
    public void handleLearningEvent( LearningEvent event ) {
        BackPropagation bp = (BackPropagation) event.getSource();
        System.out.println( bp.getCurrentIteration() + ". iteration : " + bp.getTotalNetworkError() );
    }

}
