package cn.zxf.junit.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( JUnitParamsRunner.class )
public class TestMethodParams {

    Object singleParams() {
        return new Object[] { "AAA", 1, true };
    }

    @Test
    @Parameters( method = "singleParams" )
    public void testSingle( String name, int age, boolean valid ) throws Exception {
        log.info( "name: {}, age: {}, valid: {}", name, age, valid );
    }

    // ------------
    // ------------

    Object multiParams() {
        return new Object[] { new Object[] { "AAA", 1, true }, new Object[] { "BBB", 2, false } };
    }

    @Test
    @Parameters( method = "multiParams" )
    public void testMulti( String name, int age, boolean valid ) throws Exception {
        log.info( "name: {}, age: {}, valid: {}", name, age, valid );
    }

    // 指定 "parametersFor-" 前缀
    Object parametersForTestMulti2() {
        return this.multiParams();
    }

    @Test
    @Parameters
    public void testMulti2( String name, int age, boolean valid ) throws Exception {
        log.info( "name: {}, age: {}, valid: {}", name, age, valid );
    }

}
