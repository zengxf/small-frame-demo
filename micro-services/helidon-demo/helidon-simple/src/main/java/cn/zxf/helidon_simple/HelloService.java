package cn.zxf.helidon_simple;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;

// http://localhost:8080/greet
public class HelloService {

    public static void main( String[] args ) {
        Config config = Config.create();
        ServerConfiguration serverConfig = ServerConfiguration.fromConfig( config.get( "server" ) );

        WebServer webServer = WebServer.create( serverConfig, Routing.builder()
                .get( "/greet", ( req, res ) -> res.send( "Hello World!" ) )
                .build() );
        System.out.println( "Server started at: http://localhost:" + serverConfig.port() + "\n" );

        webServer.start();
    }

}
