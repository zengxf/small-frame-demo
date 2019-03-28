package cn.zxf.junit.demo1;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RunWith( Parameterized.class )
public class TestParam {

    private final String name;
    private final int    age;

    @Parameterized.Parameters( name = "{0} : {1}" )
    public static Collection<Object[]> primeNumbers() {
        return Arrays.asList( new Object[][] { //
                { "zxf-01", 21 }, //
                { "zxf-02", 22 }, //
                { "zxf-03", 23 }, //
        } );
    }

    @Test
    public void test() {
        log.info( "name: {}, age: {}", name, age );
    }

}
