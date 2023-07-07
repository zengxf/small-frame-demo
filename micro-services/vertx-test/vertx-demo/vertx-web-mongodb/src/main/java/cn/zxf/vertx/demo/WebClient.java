package cn.zxf.vertx.demo;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;

public class WebClient extends AbstractVerticle {

    public static void main( String[] args ) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle( WebClient.class.getName() );
    }

    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();
        LocalTime start = LocalTime.now();
        vertx.setPeriodic( 1000, v -> {
            client.getNow( WebServer.PORT, "localhost", "/", r -> r.bodyHandler( buf -> {
                System.out.println( "=======================" );
                System.out.println( buf );
                System.out.println( "=======================" );
            } ) );
            if ( ChronoUnit.SECONDS.between( start, LocalTime.now() ) > 3 ) {
                vertx.close();
            }
        } );
    }
}
