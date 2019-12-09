package test.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConfig {
    public static void main( String[] args ) {
	// test1();
	Config config = ConfigFactory.parseString( "{remote={netty={tcp={hostnameb=127.0.0.100}}}}" );
	Object obj1 = config.getAnyRef( "remote" );
	log.info( "[{}]", obj1 );
	Object obj2 = config.getAnyRef( "remote.netty.tcp.hostnameb" );
	log.info( "[{}]", obj2 );
    }

    static void test1() {
	Config config = ConfigFactory.load( "agent-application.conf" );
	Config ipConfig = ConfigFactory.parseString( "ServerSys.akka.remote.netty.tcp.hostnameb=127.0.0.100" );
	Config temp = ipConfig.withFallback( config );
	Config temp1 = temp.getConfig( "ServerSys" );
	Config temp2 = config.getConfig( "ServerSys" );
	log.info( "config: {}", config );
	log.info( "ipConfig: {}", ipConfig );
	log.info( "temp: {}", temp );
	log.info( "temp1: {}", temp1 );
	log.info( "temp2: {}", temp2 );
    }
}
