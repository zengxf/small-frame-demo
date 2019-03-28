package cn.zxf.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class WebServer extends AbstractVerticle {

    static int PORT = 9091;

    public static void main( String[] args ) {
        String verticleID = WebServer.class.getName();
        System.out.println( verticleID );
        
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle( verticleID );
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router( vertx );
        router.route().handler( routingContext -> {
            routingContext.response() //
                    .putHeader( "content-type", "text/html" ) //
                    .end( "Hello World!" );
        } );
        vertx.createHttpServer().requestHandler( router::accept ).listen( PORT );
    }
}
