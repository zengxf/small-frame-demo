package cn.zxf.cobertura.demo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.zxf.cobertura.demo.App;

public class AppTest {
    private App app;

    @Before
    public void setUp() {
	app = new App();
    }

    @Test
    public void testAdd() throws InterruptedException {
	int a = 1;
	int b = 2;
	int result = app.add( a, b );
	Assert.assertEquals( a + b, result );
    }

    @Test( )
    public void testSubtract() throws InterruptedException {
	int a = 1;
	int b = 2;
	int result = app.subtract( a, b );
	System.out.println( "\nresult: " + result );
	System.out.println();
	// Assert.assertEquals( a - b, result );
    }

    @Test
    public void add() {
	int result = app.add( 3 );
	System.out.println( "\nresult: " + result );
	System.out.println();
    }

    @After
    public void tearDown() throws Exception {
    }
}
