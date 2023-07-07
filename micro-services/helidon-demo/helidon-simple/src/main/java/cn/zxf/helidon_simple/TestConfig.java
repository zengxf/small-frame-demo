package cn.zxf.helidon_simple;

import io.helidon.config.Config;
import io.helidon.webserver.ServerConfiguration;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认读取 application.yaml
 */
@Slf4j
public class TestConfig {

    public static void main(String[] args) {
        Config appCfg = Config.create().get("app");
        String greeting = appCfg.get("greeting").asString("无");
        log.info("greeting: [{}]", greeting);

        Config svrCfg = Config.create();
        ServerConfiguration serverConfig = ServerConfiguration.fromConfig(svrCfg.get("server"));
        log.info("address: [{}]", serverConfig.bindAddress());
        log.info("port: [{}]", serverConfig.port());
    }

}
