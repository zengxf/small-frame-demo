package test.jvm.reload_class.trigger;

// -verbose:class
public class TriggerMain {

    public static void main( String[] args ) {
        System.out.println( "=============" );
        System.out.println( "============= " + TriggerB.class );
    }

    static TriggerA getA() {
        return null;
    }

}
