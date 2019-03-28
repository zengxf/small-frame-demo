package cn.zxf.vertx.demo.router;

import java.text.MessageFormat;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class WebServerRouter extends AbstractVerticle {

    static int PORT = 9092;

    public static void main( String[] args ) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle( WebServerRouter.class.getName() );
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router( vertx );
        router.route( "/" ).handler( routingContext -> {
            routingContext.response() //
                    .putHeader( "content-type", "text/html" ) //
                    .end( "Hello World!" );
        } );
        router.route( "/test" ).handler( routingContext -> {
            routingContext.response() //
                    .putHeader( "content-type", "text/html" ) //
                    .end( "test!" );
        } );
        router.route( HttpMethod.GET, "/user/:id" ).handler( routingContext -> {
            String id = routingContext.request().getParam( "id" );
            String name = routingContext.request().getParam( "name" );
            String name2 = routingContext.request().getParam( "name2" );
            routingContext.response() //
                    .putHeader( "content-type", "text/html" ) //
                    .end( MessageFormat.format( "user-id: {0}, name: {1}, name-2: {2}", id, name, name2 ) );
        } );
        vertx.createHttpServer() //
                .requestHandler( router::accept ) //
                .listen( PORT );
    }
}
