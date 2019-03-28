package cn.zxf.akka.remote.demo.client;

import akka.actor.UntypedActor;
import cn.zxf.akka.remote.demo.utils.ActorCommand;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: AgentActor <br/>
 * Function: Agent actor，完成具体的工作 <br/>
 * date: 2016年8月5日 下午5:06:34 <br/>
 *
 * @author siinger
 * @version
 * @since JDK 1.7
 */
@Slf4j
public class AgentActor extends UntypedActor {

    @Override
    public void onReceive( Object message ) {
	if ( message == ActorCommand.HeartBeat_OK ) {
	    log.info( getSender().path().address().host().get() + " agent is start by client print" );
	    getContext().stop( getSelf() );
	} else if ( message == ActorCommand.DO_SOMETHING ) {
	    log.info( "agentServer do something!" );
	    getSender().tell( ActorCommand.DO_SOMETHING_OK, getSelf() );
	}
    }
}
