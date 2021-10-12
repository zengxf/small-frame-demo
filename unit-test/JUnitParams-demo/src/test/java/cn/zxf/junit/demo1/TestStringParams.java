package cn.zxf.junit.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( JUnitParamsRunner.class )
public class TestStringParams {

    @Test
    @Parameters( { "zxf, 17, false", "zxf, 22, true" } )
    public void test( String name, int age, boolean valid ) throws Exception {
        log.info( "name: {}, age: {}, valid: {}", name, age, valid );
    }

}
