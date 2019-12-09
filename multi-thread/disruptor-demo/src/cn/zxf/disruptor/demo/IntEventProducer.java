package cn.zxf.disruptor.demo;

import com.lmax.disruptor.WorkHandler;

public class IntEventProducer implements WorkHandler<IntEvent> {

    private int seq = 0;

    public void onEvent( IntEvent event ) throws Exception {
        System.out.println( "produced " + seq );
        event.setValue( ++seq );
    }

}
