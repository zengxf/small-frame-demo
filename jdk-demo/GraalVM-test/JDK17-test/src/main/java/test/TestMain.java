package test;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * <p/>
 * Created by ZXFeng on 2023/11/22
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) {
        log.info("args: [{}]", Arrays.toString(args));
        String jvmPK = "test.sign";
        log.info("jvm-arg => [-D{}={}]", jvmPK, System.getProperty(jvmPK));

        log.info("Test ----------------");
        log.info("中文");
        log.info("Test ----------------");
        log.info("M1   ----------------");
    }

}
