package cn.zxf.junit.demo1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TestAssert {

    @Test
    public void test1() {
        assertThat( true, is( true ) );
        String value = "a";
        assertThat( "应该为 a", value, is( "a" ) );
    }

    @Test
    @Ignore( "don't forget me!" )
    public void somethingWeCannotHandleRightNow() {
        System.out.println( "--------" );
    }

    @Test
    @Category( TestAssert.class ) // 此分类没什么用
    public void test2() {
        System.out.println( "--------" );
        String value = "a";
        assertThat( "应该为 a", value, is( "a" ) );
    }

}
