package cn.zxf.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

public class WebServerSimple extends AbstractVerticle {

    static int PORT = 9090;

    public static void main( String[] args ) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle( new WebServerSimple() );
    }

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler( request -> {
            // 每一次请求时，都会调用这个Handler
            HttpServerResponse response = request.response();
            response.putHeader( "content-type", "text/plain" );
            // 向响应中写入内容，并发送到前端
            response.end( "Hello World!" );
        } );

        server.listen( PORT );
    }
}
