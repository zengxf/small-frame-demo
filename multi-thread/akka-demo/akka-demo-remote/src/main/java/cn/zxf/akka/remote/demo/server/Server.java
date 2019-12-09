package cn.zxf.akka.remote.demo.server;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cn.zxf.akka.remote.demo.utils.ActorCommand;
import cn.zxf.akka.remote.demo.utils.PropertiesUtils;

/**
 * ClassName: Master <br/>
 * Function: Master，连接client，并定时发送任务给client. <br/>
 * date: 2016年8月5日 下午5:06:58 <br/>
 *
 * @author siinger
 * @version
 * @since JDK 1.7
 */
public class Server {
    public static void main( String[] args ) throws InterruptedException {
	PropertiesUtils.load( "core.properties" );
	Config config = ConfigFactory.load( "master-application.conf" );
	final ActorSystem actorSystem = ActorSystem.create( "ServerSystem", config.getConfig( "ServerSys" ) );
	actorSystem.actorOf( Props.create( ServerActor.class ), "serverActor" );

	// 创建client会话
	// Thread.sleep( 10_000L );
	// createClientChat( actorSystem );
    }

    /**
     * 创建client会话
     * 
     * @param actorSystem
     */
    static void createClientChat( final ActorSystem actorSystem ) {
	String clientIp = PropertiesUtils.get( "client.ip" );
	String clientPort = PropertiesUtils.get( "client.port" );
	String str = String.format( "akka.tcp://ClientSystem@%s:%s/user/clientActor", clientIp, clientPort );
	final ActorSelection remoteActor = actorSystem.actorSelection( str );
	final ActorRef actor = actorSystem.actorOf( Props.create( ServerActor.class ) );
	remoteActor.tell( ActorCommand.DO_SOMETHING, actor );
    }
}
