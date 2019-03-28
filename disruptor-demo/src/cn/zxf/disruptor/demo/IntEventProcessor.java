package cn.zxf.disruptor.demo;

import com.lmax.disruptor.WorkHandler;

public class IntEventProcessor implements WorkHandler<IntEvent> {

    public void onEvent( IntEvent event ) throws Exception {
        System.out.println( event.getValue() );
        event.setValue( 1 );
    }

}