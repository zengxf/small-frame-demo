package cn.zxf.helidon_simple;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;
import lombok.extern.slf4j.Slf4j;

// http://localhost:8080/greet
// http://localhost:8888/greet
@Slf4j
public class HelloService {

    public static final int DEF_PORT = 8866;

    public static void main(String[] args) {
        Config srvCfg = Config.create().get("server");
        ServerConfiguration serverConfig = ServerConfiguration.fromConfig(srvCfg);

        String path = HelloService.class.getResource("/application.yaml").getPath();
        log.info("cfg-path: [{}]", path);

        if (serverConfig.port() == 0) {
            log.warn("没读取到配置文件，使用默认端口 [{}]", DEF_PORT);
            serverConfig = ServerConfiguration.builder()
                    .port(DEF_PORT)
                    .build();
        }

        String greetPath = "/greet";
        Routing route = Routing.builder()
                .get(greetPath, (req, res) ->
                        res.send("Hello World! \n-- 中文测试")
                )
                .build();
        log.info("test-path: http://localhost:{}{}", serverConfig.port(), greetPath);
        log.info("启动服务...");

        WebServer webServer = WebServer.create(serverConfig, route);
        webServer.start();
    }

}
