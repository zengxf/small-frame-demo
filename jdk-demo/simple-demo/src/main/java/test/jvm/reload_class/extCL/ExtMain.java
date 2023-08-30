package test.jvm.reload_class.extCL;

public class ExtMain {
    public static void main( String[] args ) {
	ClassLoader cl = ExtC1.class.getClassLoader();
	while ( true ) {
	    System.out.println( cl );
	    cl = cl.getParent();
	    if ( cl == null ) {
		System.out.println( "鏃狅細BootClassLoader" );
		break;
	    }
	}
    }
}
