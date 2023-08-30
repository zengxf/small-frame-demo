package test.jdkapi.ju;

import java.util.Map;
import java.util.WeakHashMap;

public class TestWeakHashMap {

    public static void main( String[] args ) {
        String a = new String( "a" );
        String b = new String( "b" );

        Map<String, String> map = new WeakHashMap<>();
        map.put( "aa", "aa" );
        System.out.println( map );
        map.put( "bb", "bb" );
        System.out.println( map );
        System.gc();
        System.out.println( map );

        map.put( a, "aaa" );
        map.put( b, "bbb" );

        System.out.println( map );

        a = null;
        b = null; // 要为 null

        System.gc();

        System.out.println( map.size() );
        System.out.println( map ); // toString() -> entrySet().iterator() -> isEmpty() -> size()

    }

}
