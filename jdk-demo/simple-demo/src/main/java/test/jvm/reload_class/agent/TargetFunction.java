package test.jvm.reload_class.agent;

public class TargetFunction {

    public void testInstance() {
	System.out.println( "--------------" );
	System.out.println( "test Instance 111" );
	System.out.println( "--------------" );
    }

    public static void testStatic() {
	System.out.println( "--------------" );
	System.out.println( "test Static 111" );
	System.out.println( "--------------" );
    }

}
