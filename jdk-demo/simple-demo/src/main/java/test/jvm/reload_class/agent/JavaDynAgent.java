package test.jvm.reload_class.agent;

import java.lang.instrument.Instrumentation;

public class JavaDynAgent {

    private static Instrumentation instrumentation;
    private static Object	   lockObject = new Object();

    public JavaDynAgent() {
    }

    public static void agentmain( String args, Instrumentation inst ) {
	synchronized ( lockObject ) {
	    if ( instrumentation == null ) {
		instrumentation = inst;
		System.out.println( "0->" + inst );
	    } else {
		System.out.println( "1->" + inst );
	    }

	}
    }

    public static Instrumentation getInstrumentation() {
	return instrumentation;
    }

}
