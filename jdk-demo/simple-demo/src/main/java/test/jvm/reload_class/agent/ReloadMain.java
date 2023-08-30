package test.jvm.reload_class.agent;

public class ReloadMain {

    public static void main( String[] args ) throws Exception {

	ClassLoader cl = ReloadMain.class.getClassLoader();
	System.out.println( cl );

	while ( true ) {
	    TargetFunction.testStatic();
	    Thread.sleep( 1000L );
	}

    }

}
