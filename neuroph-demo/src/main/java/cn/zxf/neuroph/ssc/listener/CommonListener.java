package cn.zxf.neuroph.ssc.listener;

import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.learning.BackPropagation;

public class CommonListener implements LearningEventListener {

    public static CommonListener INSTANCE = new CommonListener();

    private double               error;

    @Override
    public void handleLearningEvent( LearningEvent event ) {
        BackPropagation bp = (BackPropagation) event.getSource();
        double curError = bp.getTotalNetworkError();
        double errorInterval = curError - error;
        error = curError;
        System.out.println( "迭代次数：" + bp.getCurrentIteration() + "，总误差：" + curError + "，进步：" + errorInterval );
        // Neuron neuron = (Neuron) bp.getNeuralNetwork().getOutputNeurons()[0];
        // for ( Connection conn : neuron.getInputConnections() ) {
        // System.out.print( conn.getWeight().value + "\t" );
        // }
        // System.out.println();
        System.out.println( "--------------------" );
    }

}
