package test.cn.zxf.junit.demo1;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * ≤‚ ‘ Test
 * 
 * @author Administrator
 */
@RunWith( JUnitPlatform.class )
@SelectPackages( { "test.cn.zxf.junit.demo1" } )
public class TestAssert {

    @Test
    public void test_1() {
        System.out.println( "test1" );
    }

    @Test
    @Disabled
    public void test_disabled() {
        System.out.println( "--------" );
    }
}
