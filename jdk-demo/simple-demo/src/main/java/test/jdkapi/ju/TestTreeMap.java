package test.jdkapi.ju;

import java.util.SortedMap;
import java.util.TreeMap;

public class TestTreeMap {

    public static void main( String[] args ) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put( 1, "1aa" );
        map.put( 2, "2aa" );
        map.put( 4, "4aa" );
        map.put( 5, "5aa" );
        System.out.println( map );

        SortedMap<Integer, String> subMap = map.tailMap( 3 );
        System.out.println( subMap );

        subMap = map.tailMap( 4 );
        System.out.println( subMap );

        subMap = map.headMap( 4 );
        System.out.println( subMap );

        subMap = map.subMap( 2, 4 );
        System.out.println( subMap );
    }

}
