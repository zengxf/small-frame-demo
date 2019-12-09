package cn.zxf.akka.remote.demo.server;

import akka.actor.UntypedActor;
import cn.zxf.akka.remote.demo.utils.ActorCommand;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: MasterActor <br/>
 * Function: 接受client消息 <br/>
 * date: 2016年8月5日 下午5:33:40 <br/>
 *
 * @author siinger
 * @version
 * @since JDK 1.7
 */
@Slf4j
public class ServerActor extends UntypedActor {

    @Override
    public void onReceive( Object message ) throws Exception {
	if ( message == ActorCommand.HeartBeat ) {
	    log.info( getSender().path().address().host().get() + " 客户端 is start by server print" );
	    getSender().tell( ActorCommand.HeartBeat_OK, getSelf() );
	} else if ( message == ActorCommand.DO_SOMETHING_OK ) {
	    log.info( getSender().path().address().host().get() + " 业务调用 ok! by server print" );
	    getContext().stop( getSelf() );
	}
    }
}
