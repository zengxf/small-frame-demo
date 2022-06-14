package cn.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestMultiLine {

    private static final org.slf4j.Logger log;
    private static final Logger logger;


    static {
        log = org.slf4j.LoggerFactory.getLogger(TestMultiLine.class);
        logger = LogManager.getLogger(TestMultiLine.class);
    }


    public static void main(String[] args) {
        log.info("test info \n 1111");
        log.warn("test warn \n 2222");
        log.error("test error \n 3333");

        logger.info("test info \n 1111");
        logger.warn("test warn \n 2222");
        logger.error("test error \n 3333");
    }

}
