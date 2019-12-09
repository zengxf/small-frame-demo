package cn.zxf.disruptor.demo2;

import com.lmax.disruptor.EventHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent( LongEvent event, long sequence, boolean endOfBatch ) {
	log.info( "Event: {}, sequence: {}, endOfBatch: {} ", event, sequence, endOfBatch );
    }
}