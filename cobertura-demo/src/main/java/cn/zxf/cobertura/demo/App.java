package cn.zxf.cobertura.demo;

public class App {
    public int add( int a, int b ) {
	return a + b;
    }

    public int subtract( int a, int b ) {
	int c = 10;
	a *= c;
	return a - b;
    }

    public int add( int a ) {
	if ( a > 10 )
	    return a * 10 + 5;
	else
	    return a * 100 + 5;
    }

}
