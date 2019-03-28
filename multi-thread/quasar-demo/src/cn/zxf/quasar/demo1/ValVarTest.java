package cn.zxf.quasar.demo1;

import java.util.concurrent.ExecutionException;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.dataflow.Val;
import co.paralleluniverse.strands.dataflow.Var;

public class ValVarTest {
    public static void main( String[] args ) throws SuspendExecution, InterruptedException, ExecutionException {
	Val<Integer> a = new Val<>();
	Var<Integer> x = new Var<>();
	Var<Integer> y = new Var<>( () -> a.get() * x.get() );
	Var<Integer> z = new Var<>( () -> a.get() + x.get() );
	Var<Integer> r = new Var<>( () -> {
	    int res = y.get() + z.get();
	    System.out.println( "res: " + res );
	    return res;
	} );

	Fiber<?> f = new Fiber<Void>( () -> {
	    for ( int i = 0; i < 10; i++ ) {
		x.set( i );
		Strand.sleep( 100 );
	    }
	} ).start();

	Strand.sleep( 2000 );
	a.set( 3 ); // this will trigger everything
	f.join();
	System.out.println( "r: " + r.get() );
    }
}
