package cn.zxf.akka.remote.demo.client;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cn.zxf.akka.remote.demo.utils.ActorCommand;
import cn.zxf.akka.remote.demo.utils.PropertiesUtils;

/**
 * ClassName: Agent <br/>
 * Function: Agent 测试类. <br/>
 * date: 2016年8月5日 下午5:05:15 <br/>
 *
 * @author siinger
 * @version
 * @since JDK 1.7
 */
public class Agent {

    public void init() {
	PropertiesUtils.load( "core.properties" );
    }

    public static void main( String[] args ) {
	// startListen();
	simpleCall();
    }

    static void simpleCall() {
	Agent agent = new Agent();
	agent.init();

	Config config = ConfigFactory.load( "agent-application.conf" );
	// 修改IP设置
	String serverIp = PropertiesUtils.get( "masterServer.ip" );
	String serverPort = PropertiesUtils.get( "masterServer.port" );

	final ActorSystem actorSystem = ActorSystem.create( "ClientSystem", config.getConfig( "ServerSys" ) );

	// 创建client发送心跳
	String str = String.format( "akka.tcp://ServerSystem@%s:%s/user/serverActor", serverIp, serverPort );
	final ActorSelection remoteActor = actorSystem.actorSelection( str );
	final ActorRef actor = actorSystem.actorOf( Props.create( AgentActor.class ) );
	remoteActor.tell( ActorCommand.HeartBeat, actor );
    }

    static void startListen() {
	Agent agent = new Agent();
	agent.init();
	// 创建remote 接受命令
	Config config = ConfigFactory.load( "agent-application.conf" );
	// 修改IP设置
	String serverIp = PropertiesUtils.get( "masterServer.ip" );
	String serverPort = PropertiesUtils.get( "masterServer.port" );

	Config ipConfig = ConfigFactory.parseString( "ServerSys.akka.remote.netty.tcp.hostname=" + serverIp );
	final ActorSystem actorSystem = ActorSystem.create( "ClientSystem", ipConfig.withFallback( config ).getConfig( "ServerSys" ) );
	actorSystem.actorOf( Props.create( AgentActor.class ), "clientActor" ); // 启动监听

	// 创建client发送心跳
	String str = String.format( "akka.tcp://ServerSystem@%s:%s/user/serverActor", serverIp, serverPort );
	final ActorSelection remoteActor = actorSystem.actorSelection( str );
	final ActorRef actor = actorSystem.actorOf( Props.create( AgentActor.class ) );
	remoteActor.tell( ActorCommand.HeartBeat, actor );
    }

}
