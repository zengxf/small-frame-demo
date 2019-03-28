package cn.zxf.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebServerSimple extends AbstractVerticle {

    static int    PORT    = Integer.getInteger( "server.port", 8088 );
    static String VERSION = "0.0.2";

    public static void main( String[] args ) {
        log.info( "port: {}", PORT );
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle( new WebServerSimple() );
        log.info( "port: {} start done!", PORT );
    }

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler( request -> {
            log.info( "request-host: {}", request.host() );

            // 每一次请求时，都会调用这个 Handler
            HttpServerResponse response = request.response();
            response.putHeader( "content-type", "text/plain" );
            // 向响应中写入内容，并发送到前端
            String content = String.format( "Hello World! version: [%s], port: [%s]", VERSION, PORT );
            response.end( content );

            log.info( "response-content: {}", content );
        } );

        try {
            server.exceptionHandler( e -> log.error( "服务异常！", e ) )
                    .listen( PORT );
        } catch ( Exception e ) {
            log.error( "服务启动异常！", e );
            vertx.close();
        }
    }

}
