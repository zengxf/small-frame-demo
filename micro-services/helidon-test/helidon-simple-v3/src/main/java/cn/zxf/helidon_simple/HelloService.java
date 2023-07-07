package cn.zxf.helidon_simple;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import lombok.extern.slf4j.Slf4j;

// http://localhost:8080/greet
@Slf4j
public class HelloService {

    public static void main(String[] args) {
        log.info("\n\n");
        String path = HelloService.class.getResource("/application.yaml").getPath();
        log.info("cfg-path: [{}]", path);

        Config srvCfg = Config.create().get("server");
        Integer port = srvCfg.get("port").asInt().orElse(null);

        if (port == null) { // 新版本能读取配置了
            throw new RuntimeException("没有读取到配置文件");
        }

        String greetPath = "/greet";
        Routing route = Routing.builder()
                .get(greetPath, (req, res) -> res
                        .addHeader("Content-Type", "text/plain; charset=UTF-8")
                        .send("Hello World! \n-- 中文测试")
                )
                .build();
        log.info("test-path: http://localhost:{}{}", port, greetPath);
        log.info("启动服务...\n\n");

        WebServer webServer = WebServer.create(route, srvCfg);
        webServer.start();
    }

}
