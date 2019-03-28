package cn.zxf.helidon_simple;

import io.helidon.config.Config;
import io.helidon.webserver.ServerConfiguration;

public class TestConfig {

    public static void main( String[] args ) {
        Config CONFIG = Config.create()
                .get( "app" );
        String greeting = CONFIG.get( "greeting" )
                .asString( "无" );
        System.out.println( greeting );

        Config config = Config.create();
        ServerConfiguration serverConfig = ServerConfiguration.fromConfig( config.get( "server" ) );
        System.out.println( serverConfig.bindAddress() );
        System.out.println( serverConfig.port() );
    }

}
