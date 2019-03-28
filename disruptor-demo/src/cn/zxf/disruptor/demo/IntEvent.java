package cn.zxf.disruptor.demo;

import com.lmax.disruptor.EventFactory;

import lombok.Data;

@Data
public class IntEvent {
    private int                          value             = -1;

    public static EventFactory<IntEvent> INT_ENEVT_FACTORY = new EventFactory<IntEvent>() {
                                                               public IntEvent newInstance() {
                                                                   return new IntEvent();
                                                               }
                                                           };
}
