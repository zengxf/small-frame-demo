package cn.zxf.quasar.demo1;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;

public class SimpleSupply {
    public static void main( String[] args ) {
	new Fiber<String>() {
	    private static final long serialVersionUID = 1L;

	    @Override
	    protected String run() throws SuspendExecution, InterruptedException {
		System.out.println( "test: " + Thread.currentThread().getName() );
		return "";
	    }
	}.start();
    }
}
