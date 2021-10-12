package cn.zxf.junit.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( JUnitParamsRunner.class )
public class TestClassParams {

    @Test
    @Parameters( source = ParamProvider.class )
    public void testSingle( String name, int age, boolean valid ) throws Exception {
        log.info( "name: {}, age: {}, valid: {}", name, age, valid );
    }

    // 方法必须是 "provide-" 前缀
    public static class ParamProvider {
        public static Object provide_singleParams() {
            return new Object[] { "AAA", 1, true };
        }

        public static Object provideMultiParams() {
            return new Object[] { new Object[] { "AAA", 1, true }, new Object[] { "BBB", 2, false } };
        }
    }

}
