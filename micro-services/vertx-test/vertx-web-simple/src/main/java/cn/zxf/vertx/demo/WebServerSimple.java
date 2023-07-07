package cn.zxf.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebServerSimple extends AbstractVerticle {

    static int PORT = Integer.getInteger("server.port", 8088);
    static String VERSION = "0.0.2";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new WebServerSimple());

        log.info("\n\n");
        log.info("port: {} 启动完成!", PORT);
        log.info("访问：http://localhost:{}\n\n", PORT);
    }

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
            log.info("request-host: {}", request.host());

            String content = String.format("Hello World! \n-- 中文测试 \n-- version: [%s]", VERSION);
            HttpServerResponse response = request.response();
            response.putHeader("content-type", "text/plain; charset=UTF-8");
            response.end(content);
        });

        try {
            server
                    .exceptionHandler(e -> log.error("服务异常！", e))
                    .listen(PORT);
        } catch (Exception e) {
            log.error("服务启动异常！", e);
            vertx.close();
        }
    }

}
