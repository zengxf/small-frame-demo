package cn.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;

@Slf4j
public class TestSimple {
    private static final Logger logger;

    static {
        PluginManager.addPackage("cn.plugin");
        logger = LogManager.getLogger(TestMultiLine.class);
    }

    public static void main(String[] args) {
        log.info("vm-param: [{}]", System.getProperty("log4j.plugin.packages"));
        log.info("test info 1111");
        logger.info("test info 2222");
    }

}
