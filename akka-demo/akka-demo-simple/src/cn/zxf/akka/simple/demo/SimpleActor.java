package cn.zxf.akka.simple.demo;

import akka.actor.UntypedActor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SimpleActor extends UntypedActor {

    private final String name;

    @Override
    public void onReceive( Object arg0 ) throws Throwable {
	log.info( "name: [{}], param: [{}]", name, arg0 );
    }

}
