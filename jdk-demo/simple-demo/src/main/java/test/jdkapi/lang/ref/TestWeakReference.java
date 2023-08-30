package test.jdkapi.lang.ref;

import java.lang.ref.WeakReference;

public class TestWeakReference {

    public static void main( String[] args ) {
        String aStr = new String( " aaa " );

        AWeakReference a = new AWeakReference( aStr, "aa" );
        AWeakReference b = new AWeakReference( new String( " bbb " ), "bb" );
        System.out.println( a );
        System.out.println( b );
        System.out.println();

        System.gc();
        System.out.println("----- gc --------");
        System.out.println( a );
        System.out.println( b );
        System.out.println();

        aStr = null; // referentObj 要为 null
        System.gc();
        System.out.println("----- gc --------");
        System.out.println( a );
    }

    public static class AWeakReference extends WeakReference<String> {

        String value;

        public AWeakReference( String referent, String value ) {
            super( referent );
            this.value = value;
        }

        @Override
        public String toString() {
            return "AWeakReference [value=" + value + ", referent=" + super.get() + "]";
        }

    }

}
