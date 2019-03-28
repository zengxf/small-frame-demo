package cn.zxf.neuroph.test1;

import java.util.Arrays;
import java.util.stream.Stream;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Perceptron;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PerceptronSample {

    @SuppressWarnings( "rawtypes" )
    public static void main( String args[] ) {
        // create training set (logical AND function)
        DataSet trainingSet = new DataSet( 2, 1 );
        trainingSet.addRow( new DataSetRow( new double[] { 0, 0 }, new double[] { 0 } ) );
        trainingSet.addRow( new DataSetRow( new double[] { 0, 1 }, new double[] { 0 } ) );
        trainingSet.addRow( new DataSetRow( new double[] { 1, 0 }, new double[] { 0 } ) );
        trainingSet.addRow( new DataSetRow( new double[] { 1, 1 }, new double[] { 1 } ) );
        // create perceptron neural network
        NeuralNetwork myPerceptron = new Perceptron( 2, 1 );

        // learn the training set
        myPerceptron.learn( trainingSet );
        Stream.of( myPerceptron.getInputNeurons() ).forEach( neuron -> {
            log.info( "{}\t{}\t{}", neuron.getInputFunction(), neuron.getError(), neuron.getWeights() );
        } );
        System.out.println( "----------------" );

        // test perceptron
        System.out.println( "Testing trained perceptron" );
        testNeuralNetwork( myPerceptron, trainingSet );
        // save trained perceptron
        myPerceptron.save( "mySamplePerceptron.nnet" );
        // load saved neural network
        NeuralNetwork loadedPerceptron = NeuralNetwork.createFromFile( "mySamplePerceptron.nnet" );

        // test loaded neural network
        System.out.println( "Testing loaded perceptron" );
        testNeuralNetwork( loadedPerceptron, trainingSet );
    }

    @SuppressWarnings( "rawtypes" )
    public static void testNeuralNetwork( NeuralNetwork nnet, DataSet tset ) {
        for ( DataSetRow dataRow : tset.getRows() ) {
            nnet.setInput( dataRow.getInput() );
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            System.out.print( "Input: " + Arrays.toString( dataRow.getInput() ) );
            System.out.println( " Output: " + Arrays.toString( networkOutput ) );
        }
    }
}
