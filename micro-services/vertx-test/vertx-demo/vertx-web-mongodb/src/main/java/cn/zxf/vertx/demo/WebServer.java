package cn.zxf.vertx.demo;

import java.text.MessageFormat;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
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
        router.route( "/" )
                .handler( routingContext -> {
                    routingContext.response() //
                            .putHeader( "content-type", "text/html" ) //
                            .end( "Hello World!" );
                } );

        // http://localhost:9091/user/z001/zxf?age=222
        router.route( HttpMethod.GET, "/user/:id/:name" )
                .handler( routingContext -> {
                    String id = routingContext.request()
                            .getParam( "id" );
                    String name = routingContext.pathParam( "name" );
                    String age = routingContext.request()
                            .getParam( "age" );

                    insertDb( id, name, age ); // async

                    JsonObject query = new JsonObject().put( "id", id );
                    this.getMongoClient()
                            .findOne( "user", query, null, ( e ) -> {
                                JsonObject result = e.result();
                                Map<String, Object> map = result == null ? null : result.getMap();

                                routingContext.response() //
                                        .putHeader( "content-type", "text/html" ) //
                                        .end( MessageFormat.format( "user-body: {0}", map ) );
                            } );
                } );
        vertx.createHttpServer()
                .requestHandler( router::accept )
                .listen( PORT );
    }

    void insertDb( String id, String name, String age ) {
        MongoClient client = this.getMongoClient();

        JsonObject body = new JsonObject().put( "id", id )
                .put( "name", name )
                .put( "age", age );

        client.insert( "user", body, res -> {
            System.out.println( "succeeded: " + res.succeeded() );
            System.out.println( "_id: " + res.result() );
            if ( res.failed() )
                res.cause()
                        .printStackTrace();
        } );
    }

    MongoClient getMongoClient() {
        JsonObject config = new JsonObject().put( "connection_string", "mongodb://120.25.56.45:9091/zxf_test" );
        MongoClient client = MongoClient.createShared( vertx, config );
        return client;
    }

}
